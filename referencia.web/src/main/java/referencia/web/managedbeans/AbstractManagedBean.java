package referencia.web.managedbeans;

import java.io.Serializable;

import javax.faces.bean.ManagedProperty;

@SuppressWarnings("serial")
public abstract class AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{loginMBean}")
    private transient LoginMBean loginMBean;

    public LoginMBean getLoginMBean() {

        return loginMBean;
    }

    public void setLoginMBean(LoginMBean loginMBean) {

        this.loginMBean = loginMBean;
    }
}
