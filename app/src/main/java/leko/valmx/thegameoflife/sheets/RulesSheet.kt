package leko.valmx.thegameoflife.sheets

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.maxkeppeler.sheets.core.Sheet
import kotlinx.android.synthetic.main.sheet_rules.*
import leko.valmx.thegameoflife.R
import leko.valmx.thegameoflife.game.GameView
import leko.valmx.thegameoflife.game.utils.GameRuleHelper
import leko.valmx.thegameoflife.recyclers.RulePresetPickerAdapter
import leko.valmx.thegameoflife.recyclers.RuleSheetAdapter

class RulesSheet(context: Context, val gameView: GameView) : Sheet(),
    RulePresetPickerAdapter.RuleSelectedListener {
    fun show(ctx: Context) {
        this.windowContext = ctx
        this.width = null

        positiveText = "Apply"
        positiveListener = {
            GameRuleHelper(ctx).saveRule((rulesRecycler.adapter as RuleSheetAdapter).ruleSet)
            gameView.actorManager.applyRuleSet()
        }


        showsDialog = true

        this.show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rulesRecycler.layoutManager = LinearLayoutManager(requireContext())
        updateUi(GameRuleHelper(requireContext()).apply { loadRules() }.ruleSet)

        btn_reset_rules.setOnClickListener {
            GameRuleHelper(requireContext()).resetRules()
            updateUi(GameRuleHelper(requireContext()).apply { loadRules() }.ruleSet)
        }

        btn_rule_presets.setOnClickListener {
            RulePresetSelectionSheet(requireContext(), gameView,this).show(requireContext())
        }

    }

    override fun onCreateLayoutView(): View {
        return View.inflate(context, R.layout.sheet_rules, null)
    }

    fun updateUi(rule: GameRuleHelper.RuleSet) {
        rulesRecycler.adapter = RuleSheetAdapter(
            requireContext(), rule
        )
        ruleinteger.text = "RuleInt: ${rule.getRuleInt()}"

    }

    override fun onRuleSelected(rule: GameRuleHelper.RuleSet) {
        updateUi(rule)
    }
}