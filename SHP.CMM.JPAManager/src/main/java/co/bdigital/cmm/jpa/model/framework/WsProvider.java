package co.bdigital.cmm.jpa.model.framework;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the WS_PROVIDERS database table.
 * 
 */
@Entity
@Table(name="WS_PROVIDERS", schema="FRAMEWORKSOA")
@NamedQuery(name="WsProvider.findAll", query="SELECT w FROM WsProvider w")
public class WsProvider implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="WS_SERVICE_NAME")
	private String wsServiceName;

	@Column(name="AUTH_BASIC_PWD")
	private String authBasicPwd;

	@Column(name="AUTH_BASIC_USER")
	private String authBasicUser;

	@Column(name="WS_CONNECTION_TIMEOUT")
	private BigDecimal wsConnectionTimeout;

	@Column(name="WS_CONTEXT")
	private String wsContext;

	@Column(name="WS_HOST")
	private String wsHost;

	@Column(name="WS_PORT")
	private BigDecimal wsPort;

	@Column(name="WS_READ_TIMEOUT")
	private BigDecimal wsReadTimeout;

	public WsProvider() {
	}

	public String getWsServiceName() {
		return this.wsServiceName;
	}

	public void setWsServiceName(String wsServiceName) {
		this.wsServiceName = wsServiceName;
	}

	public String getAuthBasicPwd() {
		return this.authBasicPwd;
	}

	public void setAuthBasicPwd(String authBasicPwd) {
		this.authBasicPwd = authBasicPwd;
	}

	public String getAuthBasicUser() {
		return this.authBasicUser;
	}

	public void setAuthBasicUser(String authBasicUser) {
		this.authBasicUser = authBasicUser;
	}

	public BigDecimal getWsConnectionTimeout() {
		return this.wsConnectionTimeout;
	}

	public void setWsConnectionTimeout(BigDecimal wsConnectionTimeout) {
		this.wsConnectionTimeout = wsConnectionTimeout;
	}

	public String getWsContext() {
		return this.wsContext;
	}

	public void setWsContext(String wsContext) {
		this.wsContext = wsContext;
	}

	public String getWsHost() {
		return this.wsHost;
	}

	public void setWsHost(String wsHost) {
		this.wsHost = wsHost;
	}

	public BigDecimal getWsPort() {
		return this.wsPort;
	}

	public void setWsPort(BigDecimal wsPort) {
		this.wsPort = wsPort;
	}

	public BigDecimal getWsReadTimeout() {
		return this.wsReadTimeout;
	}

	public void setWsReadTimeout(BigDecimal wsReadTimeout) {
		this.wsReadTimeout = wsReadTimeout;
	}

}