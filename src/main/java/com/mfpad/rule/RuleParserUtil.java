package com.mfpad.rule;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;

public class RuleParserUtil {


    private SubRules rules;
    private DBObject subRules;   // from MongoDB

    public static String LOGIC_AND = "and";
    public static String LOGIC_OR = "or";


    private DBObject getSubRules() {
        return subRules;
    }

    private void setSubRules(DBObject subRules) {
        this.subRules = subRules;
    }

    public void initializeSubRules(DBObject dbSubRules) {
        if (dbSubRules == null) return;
        BasicDBObject subRules = ((BasicDBObject) (dbSubRules));
        SubRules topSubRules = new SubRules(subRules);
        setRules(topSubRules);
    }

    public SubRules getRules() {
        return rules;
    }

    public void setRules(SubRules rules) {
        this.rules = rules;
    }

    public class BaseRule {
        public final static String TYPE_SUBRULES = "subrules";
        public final static String TYPE_RESTRICTION = "restriction";

        public BaseRule() {
            setType("uninitialized");
        }

        String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public final class SubRules extends BaseRule {
        String logic;
        List<BaseRule> subrules;

        public SubRules() {

        }

        public SubRules(BasicDBObject object) {
            String type = (String) object.get("type");
            String logic = (String) object.get("logic");
            setLogic(logic);
            setType(type);
            setSubrules(extraSubrulesList((BasicDBList) object.get("subrules")));
        }

        public List<BaseRule> extraSubrulesList(BasicDBList subRulesList) {
            List<BaseRule> baseRuleList = new ArrayList<>();
            for (Object currentSubRuleTemp : subRulesList) {
                BasicDBObject currentSubRule = (BasicDBObject) currentSubRuleTemp;
                String type = (String) currentSubRule.get("type");
                if (type.equals(TYPE_RESTRICTION)) {
                    baseRuleList.add(new Restriction(currentSubRule));
                } else if (type.equals(TYPE_SUBRULES)) {
                    baseRuleList.add(new SubRules(currentSubRule));
                }
            }
            return baseRuleList;
        }


        public String getLogic() {
            return logic;
        }

        public void setLogic(String logic) {
            this.logic = logic;
        }

        public List<BaseRule> getSubrules() {
            return subrules;
        }

        public void setSubrules(List<BaseRule> subrules) {
            this.subrules = subrules;
        }
    }

    public final class Restriction extends BaseRule {
        String factorId;
        String logicOperator;
        String leftLimit;
        String rightLimit;
        String introduce;


        public Restriction(BasicDBObject object) {
            String type = (String) object.get("type");
            String factorId = (String) object.get("factorId");
            String leftLimit = (String) object.get("leftLimit");
            String rightLimit = (String) object.get("rightLimit");
            String logicOperator = (String) object.get("logicOperator");
            String introduce = (String) object.get("introduce");
            setType(type);
            setFactorId(factorId);
            setLeftLimit(leftLimit);
            setRightLimit(rightLimit);
            setLogicOperator(logicOperator);
            setIntroduce(introduce);
        }

        public Restriction() {
        }

        public String getFactorId() {
            return factorId;
        }

        public void setFactorId(String factorId) {
            this.factorId = factorId;
        }

        public String getLogicOperator() {
            return logicOperator;
        }

        public void setLogicOperator(String logicOperator) {
            this.logicOperator = logicOperator;
        }

        public String getLeftLimit() {
            return leftLimit;
        }

        public void setLeftLimit(String leftLimit) {
            this.leftLimit = leftLimit;
        }

        public String getRightLimit() {
            return rightLimit;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public void setRightLimit(String rightLimit) {
            this.rightLimit = rightLimit;
        }
    }


}
