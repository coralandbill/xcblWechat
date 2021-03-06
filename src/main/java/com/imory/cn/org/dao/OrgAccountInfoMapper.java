package com.imory.cn.org.dao;

import com.imory.cn.org.dto.OrgAccountInfo;
import com.imory.cn.org.dto.OrgAccountInfoExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface OrgAccountInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orgaccountinfo
     *
     * @mbggenerated Sun Jan 14 15:21:48 CST 2018
     */
    @SelectProvider(type=OrgAccountInfoSqlProvider.class, method="countByExample")
    int countByExample(OrgAccountInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orgaccountinfo
     *
     * @mbggenerated Sun Jan 14 15:21:48 CST 2018
     */
    @DeleteProvider(type=OrgAccountInfoSqlProvider.class, method="deleteByExample")
    int deleteByExample(OrgAccountInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orgaccountinfo
     *
     * @mbggenerated Sun Jan 14 15:21:48 CST 2018
     */
    @Delete({
        "delete from orgaccountinfo",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orgaccountinfo
     *
     * @mbggenerated Sun Jan 14 15:21:48 CST 2018
     */
    @Insert({
        "insert into orgaccountinfo (userName, openId, ",
        "mobile, companyId, ",
        "nickName, logoUrl, ",
        "createTime)",
        "values (#{userName,jdbcType=VARCHAR}, #{openId,jdbcType=VARCHAR}, ",
        "#{mobile,jdbcType=VARCHAR}, #{companyId,jdbcType=INTEGER}, ",
        "#{nickName,jdbcType=VARCHAR}, #{logoUrl,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(OrgAccountInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orgaccountinfo
     *
     * @mbggenerated Sun Jan 14 15:21:48 CST 2018
     */
    @InsertProvider(type=OrgAccountInfoSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(OrgAccountInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orgaccountinfo
     *
     * @mbggenerated Sun Jan 14 15:21:48 CST 2018
     */
    @SelectProvider(type=OrgAccountInfoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="userName", property="userName", jdbcType= JdbcType.VARCHAR),
        @Result(column="openId", property="openId", jdbcType= JdbcType.VARCHAR),
        @Result(column="mobile", property="mobile", jdbcType= JdbcType.VARCHAR),
        @Result(column="companyId", property="companyId", jdbcType= JdbcType.INTEGER),
        @Result(column="nickName", property="nickName", jdbcType= JdbcType.VARCHAR),
        @Result(column="logoUrl", property="logoUrl", jdbcType= JdbcType.VARCHAR),
        @Result(column="createTime", property="createTime", jdbcType= JdbcType.TIMESTAMP)
    })
    List<OrgAccountInfo> selectByExample(OrgAccountInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orgaccountinfo
     *
     * @mbggenerated Sun Jan 14 15:21:48 CST 2018
     */
    @Select({
        "select",
        "id, userName, openId, mobile, companyId, nickName, logoUrl, createTime",
        "from orgaccountinfo",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
        @Result(column="userName", property="userName", jdbcType= JdbcType.VARCHAR),
        @Result(column="openId", property="openId", jdbcType= JdbcType.VARCHAR),
        @Result(column="mobile", property="mobile", jdbcType= JdbcType.VARCHAR),
        @Result(column="companyId", property="companyId", jdbcType= JdbcType.INTEGER),
        @Result(column="nickName", property="nickName", jdbcType= JdbcType.VARCHAR),
        @Result(column="logoUrl", property="logoUrl", jdbcType= JdbcType.VARCHAR),
        @Result(column="createTime", property="createTime", jdbcType= JdbcType.TIMESTAMP)
    })
    OrgAccountInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orgaccountinfo
     *
     * @mbggenerated Sun Jan 14 15:21:48 CST 2018
     */
    @UpdateProvider(type=OrgAccountInfoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") OrgAccountInfo record, @Param("example") OrgAccountInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orgaccountinfo
     *
     * @mbggenerated Sun Jan 14 15:21:48 CST 2018
     */
    @UpdateProvider(type=OrgAccountInfoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") OrgAccountInfo record, @Param("example") OrgAccountInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orgaccountinfo
     *
     * @mbggenerated Sun Jan 14 15:21:48 CST 2018
     */
    @UpdateProvider(type=OrgAccountInfoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(OrgAccountInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orgaccountinfo
     *
     * @mbggenerated Sun Jan 14 15:21:48 CST 2018
     */
    @Update({
        "update orgaccountinfo",
        "set userName = #{userName,jdbcType=VARCHAR},",
          "openId = #{openId,jdbcType=VARCHAR},",
          "mobile = #{mobile,jdbcType=VARCHAR},",
          "companyId = #{companyId,jdbcType=INTEGER},",
          "nickName = #{nickName,jdbcType=VARCHAR},",
          "logoUrl = #{logoUrl,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(OrgAccountInfo record);
}