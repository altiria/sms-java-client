package com.altiria.app;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

// Run individually
//@SuiteClasses({TestAltiriaSmsJavaClientSendSmsHttp.class})
//@SuiteClasses({TestAltiriaSmsJavaClientSendSmsAuth.class})
//@SuiteClasses({TestAltiriaSmsJavaClientSendSmsConnection.class})

//@SuiteClasses({TestAltiriaSmsJavaClientGetCreditHttp.class})
//@SuiteClasses({TestAltiriaSmsJavaClientGetCreditAuth.class})
//@SuiteClasses({TestAltiriaSmsJavaClientGetCreditConnection.class})

// Run all suites (without connection suites)
@SuiteClasses({TestAltiriaSmsJavaClientSendSmsHttp.class, TestAltiriaSmsJavaClientSendSmsAuth.class, TestAltiriaSmsJavaClientGetCreditHttp.class, TestAltiriaSmsJavaClientGetCreditAuth.class})

public class TestAll {}