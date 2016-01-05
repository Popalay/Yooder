package com.popalay.yooder.eventbus

import com.popalay.yooder.models.Debt

public final class SignupButtonEvent(val email: String = "")

public class LoginButtonEvent(val success: Boolean)

public class AddedDebtEvent(val debt: Debt)


