package com.imory.cn.inform.dao;

import com.imory.cn.inform.dto.UserInform;
import com.imory.cn.inform.dto.UserInformExample;

import java.util.List;
import java.util.Map;
import com.imory.cn.inform.dto.UserInformExample.Criteria;
import com.imory.cn.inform.dto.UserInformExample.Criterion;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class UserInformSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_inform
     *
     * @mbggenerated Sun Jan 07 16:12:52 CST 2018
     */
    public String countByExample(UserInformExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("user_inform");
        applyWhere(example, false);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_inform
     *
     * @mbggenerated Sun Jan 07 16:12:52 CST 2018
     */
    public String deleteByExample(UserInformExample example) {
        BEGIN();
        DELETE_FROM("user_inform");
        applyWhere(example, false);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_inform
     *
     * @mbggenerated Sun Jan 07 16:12:52 CST 2018
     */
    public String insertSelective(UserInform record) {
        BEGIN();
        INSERT_INTO("user_inform");
        
        if (record.getCompanyId() != null) {
            VALUES("companyId", "#{companyId,jdbcType=INTEGER}");
        }
        
        if (record.getOrgaccoId() != null) {
            VALUES("orgaccoId", "#{orgaccoId,jdbcType=INTEGER}");
        }
        
        if (record.getTitle() != null) {
            VALUES("title", "#{title,jdbcType=VARCHAR}");
        }
        
        if (record.getContent() != null) {
            VALUES("content", "#{content,jdbcType=VARCHAR}");
        }
        
        if (record.getMobile() != null) {
            VALUES("mobile", "#{mobile,jdbcType=VARCHAR}");
        }
        
        if (record.getState() != null) {
            VALUES("state", "#{state,jdbcType=INTEGER}");
        }
        
        if (record.getReplayContent() != null) {
            VALUES("replayContent", "#{replayContent,jdbcType=VARCHAR}");
        }
        
        if (record.getReplayDate() != null) {
            VALUES("replayDate", "#{replayDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("createTime", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getImgUrl() != null) {
            VALUES("imgUrl", "#{imgUrl,jdbcType=LONGVARCHAR}");
        }
        
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_inform
     *
     * @mbggenerated Sun Jan 07 16:12:52 CST 2018
     */
    public String selectByExampleWithBLOBs(UserInformExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("companyId");
        SELECT("orgaccoId");
        SELECT("title");
        SELECT("content");
        SELECT("mobile");
        SELECT("state");
        SELECT("replayContent");
        SELECT("replayDate");
        SELECT("createTime");
        SELECT("imgUrl");
        FROM("user_inform");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_inform
     *
     * @mbggenerated Sun Jan 07 16:12:52 CST 2018
     */
    public String selectByExample(UserInformExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("companyId");
        SELECT("orgaccoId");
        SELECT("title");
        SELECT("content");
        SELECT("mobile");
        SELECT("state");
        SELECT("replayContent");
        SELECT("replayDate");
        SELECT("createTime");
        FROM("user_inform");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_inform
     *
     * @mbggenerated Sun Jan 07 16:12:52 CST 2018
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        UserInform record = (UserInform) parameter.get("record");
        UserInformExample example = (UserInformExample) parameter.get("example");
        
        BEGIN();
        UPDATE("user_inform");
        
        if (record.getId() != null) {
            SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getCompanyId() != null) {
            SET("companyId = #{record.companyId,jdbcType=INTEGER}");
        }
        
        if (record.getOrgaccoId() != null) {
            SET("orgaccoId = #{record.orgaccoId,jdbcType=INTEGER}");
        }
        
        if (record.getTitle() != null) {
            SET("title = #{record.title,jdbcType=VARCHAR}");
        }
        
        if (record.getContent() != null) {
            SET("content = #{record.content,jdbcType=VARCHAR}");
        }
        
        if (record.getMobile() != null) {
            SET("mobile = #{record.mobile,jdbcType=VARCHAR}");
        }
        
        if (record.getState() != null) {
            SET("state = #{record.state,jdbcType=INTEGER}");
        }
        
        if (record.getReplayContent() != null) {
            SET("replayContent = #{record.replayContent,jdbcType=VARCHAR}");
        }
        
        if (record.getReplayDate() != null) {
            SET("replayDate = #{record.replayDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateTime() != null) {
            SET("createTime = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getImgUrl() != null) {
            SET("imgUrl = #{record.imgUrl,jdbcType=LONGVARCHAR}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_inform
     *
     * @mbggenerated Sun Jan 07 16:12:52 CST 2018
     */
    public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("user_inform");
        
        SET("id = #{record.id,jdbcType=INTEGER}");
        SET("companyId = #{record.companyId,jdbcType=INTEGER}");
        SET("orgaccoId = #{record.orgaccoId,jdbcType=INTEGER}");
        SET("title = #{record.title,jdbcType=VARCHAR}");
        SET("content = #{record.content,jdbcType=VARCHAR}");
        SET("mobile = #{record.mobile,jdbcType=VARCHAR}");
        SET("state = #{record.state,jdbcType=INTEGER}");
        SET("replayContent = #{record.replayContent,jdbcType=VARCHAR}");
        SET("replayDate = #{record.replayDate,jdbcType=TIMESTAMP}");
        SET("createTime = #{record.createTime,jdbcType=TIMESTAMP}");
        SET("imgUrl = #{record.imgUrl,jdbcType=LONGVARCHAR}");
        
        UserInformExample example = (UserInformExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_inform
     *
     * @mbggenerated Sun Jan 07 16:12:52 CST 2018
     */
    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("user_inform");
        
        SET("id = #{record.id,jdbcType=INTEGER}");
        SET("companyId = #{record.companyId,jdbcType=INTEGER}");
        SET("orgaccoId = #{record.orgaccoId,jdbcType=INTEGER}");
        SET("title = #{record.title,jdbcType=VARCHAR}");
        SET("content = #{record.content,jdbcType=VARCHAR}");
        SET("mobile = #{record.mobile,jdbcType=VARCHAR}");
        SET("state = #{record.state,jdbcType=INTEGER}");
        SET("replayContent = #{record.replayContent,jdbcType=VARCHAR}");
        SET("replayDate = #{record.replayDate,jdbcType=TIMESTAMP}");
        SET("createTime = #{record.createTime,jdbcType=TIMESTAMP}");
        
        UserInformExample example = (UserInformExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_inform
     *
     * @mbggenerated Sun Jan 07 16:12:52 CST 2018
     */
    public String updateByPrimaryKeySelective(UserInform record) {
        BEGIN();
        UPDATE("user_inform");
        
        if (record.getCompanyId() != null) {
            SET("companyId = #{companyId,jdbcType=INTEGER}");
        }
        
        if (record.getOrgaccoId() != null) {
            SET("orgaccoId = #{orgaccoId,jdbcType=INTEGER}");
        }
        
        if (record.getTitle() != null) {
            SET("title = #{title,jdbcType=VARCHAR}");
        }
        
        if (record.getContent() != null) {
            SET("content = #{content,jdbcType=VARCHAR}");
        }
        
        if (record.getMobile() != null) {
            SET("mobile = #{mobile,jdbcType=VARCHAR}");
        }
        
        if (record.getState() != null) {
            SET("state = #{state,jdbcType=INTEGER}");
        }
        
        if (record.getReplayContent() != null) {
            SET("replayContent = #{replayContent,jdbcType=VARCHAR}");
        }
        
        if (record.getReplayDate() != null) {
            SET("replayDate = #{replayDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateTime() != null) {
            SET("createTime = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getImgUrl() != null) {
            SET("imgUrl = #{imgUrl,jdbcType=LONGVARCHAR}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_inform
     *
     * @mbggenerated Sun Jan 07 16:12:52 CST 2018
     */
    protected void applyWhere(UserInformExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            WHERE(sb.toString());
        }
    }
}