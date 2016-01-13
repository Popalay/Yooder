package com.popalay.yooder.eventbus

import com.popalay.yooder.models.Debt
import com.popalay.yooder.models.Other

public final class SignupButtonEvent(val email: String = "")

public class LoginButtonEvent(val success: Boolean)

public class AddedDebtEvent(val debt: Debt)

public class AddedOtherEvent(val other: Other)


