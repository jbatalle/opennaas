/**
 * This file was auto-generated by mofcomp -j version 1.0.0 on Wed Jan 12 
 * 09:21:06 CET 2011. 
 */

package net.i2cat.mantychore.model;

import java.util.*;
import java.io.*;
import java.lang.Exception;

/**
 * This Class contains accessor and mutator methods for all properties defined 
 * in the CIM class ISCSILoginStatistics as well as methods comparable to the 
 * invokeMethods defined for this class. This Class implements the 
 * ISCSILoginStatisticsBean Interface. The CIM class ISCSILoginStatistics is 
 * described as follows: 
 * 
 * Statistics for Logins and Logouts to or from an iSCSI Node. An instance of 
 * this class will be associated by ElementStatisticalData to an instance of 
 * SCSIProtocolController that represents the Node. The Node can be either an 
 * Initiator or Target and so the interpretation of the properties in this 
 * class varies accordingly. 
 */
public class ISCSILoginStatistics extends StatisticalData implements 
    Serializable {

    /**
     * This constructor creates a ISCSILoginStatisticsBeanImpl Class which 
     * implements the ISCSILoginStatisticsBean Interface, and encapsulates 
     * the CIM class ISCSILoginStatistics in a Java Bean. The CIM class 
     * ISCSILoginStatistics is described as follows: 
     * 
     * Statistics for Logins and Logouts to or from an iSCSI Node. An instance 
     * of this class will be associated by ElementStatisticalData to an 
     * instance of SCSIProtocolController that represents the Node. The Node 
     * can be either an Initiator or Target and so the interpretation of the 
     * properties in this class varies accordingly. 
     */
    public ISCSILoginStatistics(){};
    /**
     * The following constants are defined for use with the ValueMap/Values 
     * qualified property loginFailures. 
     */
    private long loginFailures;
    /**
     * This method returns the iSCSILoginStatistics.loginFailures property 
     * value. This property is described as follows: 
     * 
     * This property counts the number of times a login attempt from this 
     * node(initiator) or to this node(target) has failed. 
     * 
     * @return	long	current loginFailures property value
     * @exception	Exception	
     */
    public long getLoginFailures(){

    return this.loginFailures;
    } // getLoginFailures

    /**
     * This method sets the iSCSILoginStatistics.loginFailures property value. 
     * This property is described as follows: 
     * 
     * This property counts the number of times a login attempt from this 
     * node(initiator) or to this node(target) has failed. 
     * 
     * @param	long	new loginFailures property value
     * @exception	Exception	
     */
    public void setLoginFailures(long loginFailures) {

    this.loginFailures = loginFailures;
    } // setLoginFailures


    /**
     * The following constants are defined for use with the ValueMap/Values 
     * qualified property lastLoginFailureTime. 
     */
    private Date lastLoginFailureTime;
    /**
     * This method returns the iSCSILoginStatistics.lastLoginFailureTime 
     * property value. This property is described as follows: 
     * 
     * The timestamp of the most recent failure of a login attempt from this 
     * node(initiator) or to this node(target). A value of zero indicates 
     * that no such failures have occurred since the last system boot. 
     * 
     * @return	Date	current lastLoginFailureTime property value
     * @exception	Exception	
     */
    public Date getLastLoginFailureTime(){

    return this.lastLoginFailureTime;
    } // getLastLoginFailureTime

    /**
     * This method sets the iSCSILoginStatistics.lastLoginFailureTime property 
     * value. This property is described as follows: 
     * 
     * The timestamp of the most recent failure of a login attempt from this 
     * node(initiator) or to this node(target). A value of zero indicates 
     * that no such failures have occurred since the last system boot. 
     * 
     * @param	Date	new lastLoginFailureTime property value
     * @exception	Exception	
     */
    public void setLastLoginFailureTime(Date lastLoginFailureTime) {

    this.lastLoginFailureTime = lastLoginFailureTime;
    } // setLastLoginFailureTime


    /**
     * The following constants are defined for use with the ValueMap/Values 
     * qualified property LastLoginFailureType. 
     */

    public enum LastLoginFailureType{
    OTHER,
    NEGOTIATE,
    AUTHENTICATE,
    AUTHORIZE,
    REDIRECT
    }
    private LastLoginFailureType lastLoginFailureType;
    /**
     * This method returns the iSCSILoginStatistics.lastLoginFailureType 
     * property value. This property is described as follows: 
     * 
     * The type of the most recent failure of a login attempt from this 
     * node(initiator) or to this node(target). 
     * 
     * @return	int	current lastLoginFailureType property value
     * @exception	Exception	
     */
    public LastLoginFailureType getLastLoginFailureType(){

    return this.lastLoginFailureType;
    } // getLastLoginFailureType

    /**
     * This method sets the iSCSILoginStatistics.lastLoginFailureType property 
     * value. This property is described as follows: 
     * 
     * The type of the most recent failure of a login attempt from this 
     * node(initiator) or to this node(target). 
     * 
     * @param	int	new lastLoginFailureType property value
     * @exception	Exception	
     */
    public void setLastLoginFailureType(LastLoginFailureType 
	lastLoginFailureType){

    this.lastLoginFailureType = lastLoginFailureType;
    } // setLastLoginFailureType


    /**
     * The following constants are defined for use with the ValueMap/Values 
     * qualified property otherLastLoginFailureType. 
     */
    private String otherLastLoginFailureType;
    /**
     * This method returns the iSCSILoginStatistics.otherLastLoginFailureType 
     * property value. This property is described as follows: 
     * 
     * A string describing the type of the last login failure when 
     * LastLoginFailureType is equal to the value 1, "Other". 
     * 
     * @return	String	current otherLastLoginFailureType property 
     * value 
     * @exception	Exception	
     */
    public String getOtherLastLoginFailureType(){

    return this.otherLastLoginFailureType;
    } // getOtherLastLoginFailureType

    /**
     * This method sets the iSCSILoginStatistics.otherLastLoginFailureType 
     * property value. This property is described as follows: 
     * 
     * A string describing the type of the last login failure when 
     * LastLoginFailureType is equal to the value 1, "Other". 
     * 
     * @param	String	new otherLastLoginFailureType property 
     * value 
     * @exception	Exception	
     */
    public void setOtherLastLoginFailureType(String otherLastLoginFailureType) 
	{

    this.otherLastLoginFailureType = otherLastLoginFailureType;
    } // setOtherLastLoginFailureType


    /**
     * The following constants are defined for use with the ValueMap/Values 
     * qualified property lastLoginFailureRemoteNodeName. 
     */
    private String lastLoginFailureRemoteNodeName;
    /**
     * This method returns the 
     * iSCSILoginStatistics.lastLoginFailureRemoteNodeName property value. 
     * This property is described as follows: 
     * 
     * The iSCSI name of the remote node that failed the last login attempt.
     * 
     * @return	String	current lastLoginFailureRemoteNodeName 
     * property value 
     * @exception	Exception	
     */
    public String getLastLoginFailureRemoteNodeName(){

    return this.lastLoginFailureRemoteNodeName;
    } // getLastLoginFailureRemoteNodeName

    /**
     * This method sets the 
     * iSCSILoginStatistics.lastLoginFailureRemoteNodeName property value. 
     * This property is described as follows: 
     * 
     * The iSCSI name of the remote node that failed the last login attempt.
     * 
     * @param	String	new lastLoginFailureRemoteNodeName property 
     * value 
     * @exception	Exception	
     */
    public void setLastLoginFailureRemoteNodeName(String 
	lastLoginFailureRemoteNodeName) {

    this.lastLoginFailureRemoteNodeName = lastLoginFailureRemoteNodeName;
    } // setLastLoginFailureRemoteNodeName


    /**
     * The following constants are defined for use with the ValueMap/Values 
     * qualified property LastLoginFailureRemoteAddressType. 
     */

    public enum LastLoginFailureRemoteAddressType{
    IPV4,
    IPV6
    }
    private LastLoginFailureRemoteAddressType 
	lastLoginFailureRemoteAddressType;
    /**
     * This method returns the 
     * iSCSILoginStatistics.lastLoginFailureRemoteAddressType property value. 
     * This property is described as follows: 
     * 
     * The type of Internet Network Address of the remote node that failed the 
     * last login attempt. 
     * 
     * @return	int	current lastLoginFailureRemoteAddressType 
     * property value 
     * @exception	Exception	
     */
    public LastLoginFailureRemoteAddressType 
	getLastLoginFailureRemoteAddressType(){

    return this.lastLoginFailureRemoteAddressType;
    } // getLastLoginFailureRemoteAddressType

    /**
     * This method sets the 
     * iSCSILoginStatistics.lastLoginFailureRemoteAddressType property value. 
     * This property is described as follows: 
     * 
     * The type of Internet Network Address of the remote node that failed the 
     * last login attempt. 
     * 
     * @param	int	new lastLoginFailureRemoteAddressType property 
     * value 
     * @exception	Exception	
     */
    public void 
	setLastLoginFailureRemoteAddressType(LastLoginFailureRemoteAddressType 
	lastLoginFailureRemoteAddressType){

    this.lastLoginFailureRemoteAddressType = 
	lastLoginFailureRemoteAddressType;
    } // setLastLoginFailureRemoteAddressType


    /**
     * The following constants are defined for use with the ValueMap/Values 
     * qualified property lastLoginFailureRemoteAddress. 
     */
    private long lastLoginFailureRemoteAddress;
    /**
     * This method returns the 
     * iSCSILoginStatistics.lastLoginFailureRemoteAddress property value. 
     * This property is described as follows: 
     * 
     * An Internet Network Address giving the host address of the remote node 
     * that failed the last login attempt. 
     * 
     * @return	long	current lastLoginFailureRemoteAddress 
     * property value 
     * @exception	Exception	
     */
    public long getLastLoginFailureRemoteAddress(){

    return this.lastLoginFailureRemoteAddress;
    } // getLastLoginFailureRemoteAddress

    /**
     * This method sets the iSCSILoginStatistics.lastLoginFailureRemoteAddress 
     * property value. This property is described as follows: 
     * 
     * An Internet Network Address giving the host address of the remote node 
     * that failed the last login attempt. 
     * 
     * @param	long	new lastLoginFailureRemoteAddress property 
     * value 
     * @exception	Exception	
     */
    public void setLastLoginFailureRemoteAddress(long 
	lastLoginFailureRemoteAddress) {

    this.lastLoginFailureRemoteAddress = lastLoginFailureRemoteAddress;
    } // setLastLoginFailureRemoteAddress


    /**
     * The following constants are defined for use with the ValueMap/Values 
     * qualified property successfulLogins. 
     */
    private long successfulLogins;
    /**
     * This method returns the iSCSILoginStatistics.successfulLogins property 
     * value. This property is described as follows: 
     * 
     * The count of Login Response PDUs with status 0x0000, Accept Login, 
     * received by this node(initator), or transmitted by this node (target). 
     * 
     * @return	long	current successfulLogins property value
     * @exception	Exception	
     */
    public long getSuccessfulLogins(){

    return this.successfulLogins;
    } // getSuccessfulLogins

    /**
     * This method sets the iSCSILoginStatistics.successfulLogins property 
     * value. This property is described as follows: 
     * 
     * The count of Login Response PDUs with status 0x0000, Accept Login, 
     * received by this node(initator), or transmitted by this node (target). 
     * 
     * @param	long	new successfulLogins property value
     * @exception	Exception	
     */
    public void setSuccessfulLogins(long successfulLogins) {

    this.successfulLogins = successfulLogins;
    } // setSuccessfulLogins


    /**
     * The following constants are defined for use with the ValueMap/Values 
     * qualified property negotiationLoginFailures. 
     */
    private long negotiationLoginFailures;
    /**
     * This method returns the iSCSILoginStatistics.negotiationLoginFailures 
     * property value. This property is described as follows: 
     * 
     * If the node is an initiator this property is the number of times it has 
     * aborted a login because parameter negotiation with the target failed. 
     * If the node is a target the property is the number of times a it has 
     * effectively refused a login because the parameter negotiation failed. 
     * While this situation can occur, the exact mechanism is as yet 
     * undefined in the iSCSI Protocol Spec. 
     * 
     * @return	long	current negotiationLoginFailures property 
     * value 
     * @exception	Exception	
     */
    public long getNegotiationLoginFailures(){

    return this.negotiationLoginFailures;
    } // getNegotiationLoginFailures

    /**
     * This method sets the iSCSILoginStatistics.negotiationLoginFailures 
     * property value. This property is described as follows: 
     * 
     * If the node is an initiator this property is the number of times it has 
     * aborted a login because parameter negotiation with the target failed. 
     * If the node is a target the property is the number of times a it has 
     * effectively refused a login because the parameter negotiation failed. 
     * While this situation can occur, the exact mechanism is as yet 
     * undefined in the iSCSI Protocol Spec. 
     * 
     * @param	long	new negotiationLoginFailures property value
     * @exception	Exception	
     */
    public void setNegotiationLoginFailures(long negotiationLoginFailures) {

    this.negotiationLoginFailures = negotiationLoginFailures;
    } // setNegotiationLoginFailures


    /**
     * The following constants are defined for use with the ValueMap/Values 
     * qualified property authenticationLoginFailures. 
     */
    private long authenticationLoginFailures;
    /**
     * This method returns the 
     * iSCSILoginStatistics.authenticationLoginFailures property value. This 
     * property is described as follows: 
     * 
     * If the node is an initiator this property is the number of times it has 
     * aborted a login because the target could not be authenticated. If the 
     * node is a target this property is the count of Login Response PDUs 
     * with status 0x0201, Authentication Failed, transmitted by this target. 
     * 
     * @return	long	current authenticationLoginFailures property 
     * value 
     * @exception	Exception	
     */
    public long getAuthenticationLoginFailures(){

    return this.authenticationLoginFailures;
    } // getAuthenticationLoginFailures

    /**
     * This method sets the iSCSILoginStatistics.authenticationLoginFailures 
     * property value. This property is described as follows: 
     * 
     * If the node is an initiator this property is the number of times it has 
     * aborted a login because the target could not be authenticated. If the 
     * node is a target this property is the count of Login Response PDUs 
     * with status 0x0201, Authentication Failed, transmitted by this target. 
     * 
     * @param	long	new authenticationLoginFailures property 
     * value 
     * @exception	Exception	
     */
    public void setAuthenticationLoginFailures(long 
	authenticationLoginFailures) {

    this.authenticationLoginFailures = authenticationLoginFailures;
    } // setAuthenticationLoginFailures


    /**
     * The following constants are defined for use with the ValueMap/Values 
     * qualified property authorizationLoginFailures. 
     */
    private long authorizationLoginFailures;
    /**
     * This method returns the iSCSILoginStatistics.authorizationLoginFailures 
     * property value. This property is described as follows: 
     * 
     * If the node is an initiator this property is the count of Login 
     * Response PDUs with status class 0x201, 'Authentication Failed', 
     * received by this initiator. If the node is a target the property is 
     * the count of Login Response PDUs with status 0x0202, 'Forbidden 
     * Target', transmitted by this target. 
     * 
     * @return	long	current authorizationLoginFailures property 
     * value 
     * @exception	Exception	
     */
    public long getAuthorizationLoginFailures(){

    return this.authorizationLoginFailures;
    } // getAuthorizationLoginFailures

    /**
     * This method sets the iSCSILoginStatistics.authorizationLoginFailures 
     * property value. This property is described as follows: 
     * 
     * If the node is an initiator this property is the count of Login 
     * Response PDUs with status class 0x201, 'Authentication Failed', 
     * received by this initiator. If the node is a target the property is 
     * the count of Login Response PDUs with status 0x0202, 'Forbidden 
     * Target', transmitted by this target. 
     * 
     * @param	long	new authorizationLoginFailures property value
     * @exception	Exception	
     */
    public void setAuthorizationLoginFailures(long authorizationLoginFailures) 
	{

    this.authorizationLoginFailures = authorizationLoginFailures;
    } // setAuthorizationLoginFailures


    /**
     * The following constants are defined for use with the ValueMap/Values 
     * qualified property loginRedirects. 
     */
    private long loginRedirects;
    /**
     * This method returns the iSCSILoginStatistics.loginRedirects property 
     * value. This property is described as follows: 
     * 
     * The count of Login Response PDUs with status class 0x01, Redirection, 
     * received by this node(initiator) or transmitted by this node(target). 
     * 
     * @return	long	current loginRedirects property value
     * @exception	Exception	
     */
    public long getLoginRedirects(){

    return this.loginRedirects;
    } // getLoginRedirects

    /**
     * This method sets the iSCSILoginStatistics.loginRedirects property 
     * value. This property is described as follows: 
     * 
     * The count of Login Response PDUs with status class 0x01, Redirection, 
     * received by this node(initiator) or transmitted by this node(target). 
     * 
     * @param	long	new loginRedirects property value
     * @exception	Exception	
     */
    public void setLoginRedirects(long loginRedirects) {

    this.loginRedirects = loginRedirects;
    } // setLoginRedirects


    /**
     * The following constants are defined for use with the ValueMap/Values 
     * qualified property otherLoginFailures. 
     */
    private long otherLoginFailures;
    /**
     * This method returns the iSCSILoginStatistics.otherLoginFailures 
     * property value. This property is described as follows: 
     * 
     * The number of failure Login Response PDUs which were received by this 
     * node(initiator) or were transmitted by this node(target), and which 
     * were not counted by any other property in this class. 
     * 
     * @return	long	current otherLoginFailures property value
     * @exception	Exception	
     */
    public long getOtherLoginFailures(){

    return this.otherLoginFailures;
    } // getOtherLoginFailures

    /**
     * This method sets the iSCSILoginStatistics.otherLoginFailures property 
     * value. This property is described as follows: 
     * 
     * The number of failure Login Response PDUs which were received by this 
     * node(initiator) or were transmitted by this node(target), and which 
     * were not counted by any other property in this class. 
     * 
     * @param	long	new otherLoginFailures property value
     * @exception	Exception	
     */
    public void setOtherLoginFailures(long otherLoginFailures) {

    this.otherLoginFailures = otherLoginFailures;
    } // setOtherLoginFailures


    /**
     * The following constants are defined for use with the ValueMap/Values 
     * qualified property normalLogouts. 
     */
    private long normalLogouts;
    /**
     * This method returns the iSCSILoginStatistics.normalLogouts property 
     * value. This property is described as follows: 
     * 
     * The count of Logout Command PDUs generated by this node (initiator) or 
     * received by this node(target), with reason code 0 (closes the 
     * session). 
     * 
     * @return	long	current normalLogouts property value
     * @exception	Exception	
     */
    public long getNormalLogouts(){

    return this.normalLogouts;
    } // getNormalLogouts

    /**
     * This method sets the iSCSILoginStatistics.normalLogouts property value. 
     * This property is described as follows: 
     * 
     * The count of Logout Command PDUs generated by this node (initiator) or 
     * received by this node(target), with reason code 0 (closes the 
     * session). 
     * 
     * @param	long	new normalLogouts property value
     * @exception	Exception	
     */
    public void setNormalLogouts(long normalLogouts) {

    this.normalLogouts = normalLogouts;
    } // setNormalLogouts


    /**
     * The following constants are defined for use with the ValueMap/Values 
     * qualified property otherLogouts. 
     */
    private long otherLogouts;
    /**
     * This method returns the iSCSILoginStatistics.otherLogouts property 
     * value. This property is described as follows: 
     * 
     * The count of Logout Command PDUs generated by this node, (initiator) or 
     * received by this node(target), with any reason code other than 0. 
     * 
     * @return	long	current otherLogouts property value
     * @exception	Exception	
     */
    public long getOtherLogouts(){

    return this.otherLogouts;
    } // getOtherLogouts

    /**
     * This method sets the iSCSILoginStatistics.otherLogouts property value. 
     * This property is described as follows: 
     * 
     * The count of Logout Command PDUs generated by this node, (initiator) or 
     * received by this node(target), with any reason code other than 0. 
     * 
     * @param	long	new otherLogouts property value
     * @exception	Exception	
     */
    public void setOtherLogouts(long otherLogouts) {

    this.otherLogouts = otherLogouts;
    } // setOtherLogouts



} // Class ISCSILoginStatistics