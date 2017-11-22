package com.mfpad.rule;

import java.util.List;

public class Main {

    public static int calcuAchievedAmountFromSubRulesList(List<RuleParserUtil.BaseRule> subRulesArr) {
        int achievedAmount = 0;
        for (RuleParserUtil.BaseRule subRule : subRulesArr) {
            if (subRule != null && isAchievedRestriction(subRule)) {
                achievedAmount++;
            }
        }
        return achievedAmount;
    }

    // 判断描述是否命中
    public static boolean isAchievedRestriction(RuleParserUtil.Restriction restriction) {
//        logger.debug("msg[isAchievedRestriction] factor[{}] event[{}] opera[{}] limit:[{}-{}] result[{}]",
//                restriction.getIntroduce(),
//                restriction.getFactorId(),
//                restriction.getLogicOperator(),
//                restriction.getLeftLimit(),
//                restriction.getRightLimit()
//        );
        // your business code




        return false;
    }

    // 判断描述是否命中
    public static boolean isAchievedRestriction(RuleParserUtil.BaseRule restriction) {
        if (restriction.getType().equals(RuleParserUtil.BaseRule.TYPE_RESTRICTION)) {
            return isAchievedRestriction((RuleParserUtil.Restriction) restriction);
        } else if (restriction.getType().equals(RuleParserUtil.BaseRule.TYPE_SUBRULES)) {
            return isAchievedRestriction((RuleParserUtil.SubRules) restriction);
        } else {
            return false;
        }
    }

    public static boolean isAchievedRestriction(RuleParserUtil.SubRules subRules) {
//        logger.trace("Got into a deeper level subRule, executing...");
        int achievedAmount = calcuAchievedAmountFromSubRulesList(subRules.getSubrules());
//        logger.trace("Got out and executed this subRule, logic {}, achievedAmount {}",
//                subRules.getLogic(),
//                achievedAmount
//        );

        int achievedAmountDemand = 0;
        if (subRules.getLogic().equals(RuleParserUtil.LOGIC_AND)) {
            achievedAmountDemand = subRules.getSubrules().size();
        } else if (subRules.getLogic().equals(RuleParserUtil.LOGIC_OR)) {
            achievedAmountDemand = 1;
        }
        return (achievedAmount >= achievedAmountDemand);
    }

    public static void main(String[] args) {
        String ruleJsonString = "{\n" +
                "    \"type\": \"subrules\",\n" +
                "    \"logic\": \"and\",\n" +
                "    \"subrules\": [\n" +
                "        {\n" +
                "            \"type\": \"restriction\",\n" +
                "            \"introduce\": \"some information here\",\n" +
                "            \"factorId\": \"59cb754b61a5396edb381983\",\n" +
                "            \"logicOperator\": \"lessThan\",\n" +
                "            \"leftLimit\": \"5\",\n" +
                "            \"rightLimit\": \"50\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": \"subrules\",\n" +
                "            \"logic\": \"or\",\n" +
                "            \"subrules\": [\n" +
                "                {\n" +
                "                    \"type\": \"restriction\",\n" +
                "                    \"introduce\": \"some information here\",\n" +
                "                    \"factorId\": \"59cb754b61a5396edb381983\",\n" +
                "                    \"logicOperator\": \"equal\",\n" +
                "                    \"leftLimit\": \"30\",\n" +
                "                    \"rightLimit\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": \"restriction\",\n" +
                "                    \"introduce\": \"some information here\",\n" +
                "                    \"factorId\": \"59cb75a161a5396ee149dac5\",\n" +
                "                    \"logicOperator\": \"greaterThan\",\n" +
                "                    \"leftLimit\": \"20\",\n" +
                "                    \"rightLimit\": \"\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        System.out.println(ruleJsonString);

        RuleParserUtil ruleParserUtil = new RuleParserUtil();
        ruleParserUtil.initializeSubRules(null);  //JSON Entity from MongoDB looks like up, not from this String

        RuleParserUtil.BaseRule subRules = ruleParserUtil.getRules();
        if (subRules != null && isAchievedRestriction(subRules)) {
//            logger.trace("Top-subRule achieved");
        }
    }
}
