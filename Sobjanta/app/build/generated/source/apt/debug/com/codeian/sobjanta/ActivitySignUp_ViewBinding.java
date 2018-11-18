// Generated code from Butter Knife. Do not modify!
package com.codeian.sobjanta;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActivitySignUp_ViewBinding implements Unbinder {
  private ActivitySignUp target;

  @UiThread
  public ActivitySignUp_ViewBinding(ActivitySignUp target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActivitySignUp_ViewBinding(ActivitySignUp target, View source) {
    this.target = target;

    target._nameText = Utils.findRequiredViewAsType(source, R.id.input_name, "field '_nameText'", EditText.class);
    target._regText = Utils.findRequiredViewAsType(source, R.id.input_reg, "field '_regText'", EditText.class);
    target._emailText = Utils.findRequiredViewAsType(source, R.id.input_email, "field '_emailText'", EditText.class);
    target._passwordText = Utils.findRequiredViewAsType(source, R.id.input_password, "field '_passwordText'", EditText.class);
    target._securityText = Utils.findRequiredViewAsType(source, R.id.input_security, "field '_securityText'", EditText.class);
    target._signupButton = Utils.findRequiredViewAsType(source, R.id.btn_signup, "field '_signupButton'", Button.class);
    target._loginLink = Utils.findRequiredViewAsType(source, R.id.link_login, "field '_loginLink'", TextView.class);
    target._userGroup = Utils.findRequiredViewAsType(source, R.id.userType, "field '_userGroup'", RadioGroup.class);
    target._regContainer = Utils.findRequiredViewAsType(source, R.id.input_reg_container, "field '_regContainer'", TextInputLayout.class);
    target._securityContainer = Utils.findRequiredViewAsType(source, R.id.input_security_container, "field '_securityContainer'", TextInputLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActivitySignUp target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._nameText = null;
    target._regText = null;
    target._emailText = null;
    target._passwordText = null;
    target._securityText = null;
    target._signupButton = null;
    target._loginLink = null;
    target._userGroup = null;
    target._regContainer = null;
    target._securityContainer = null;
  }
}
