package com.altiria.app;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

// Run individually
//@SuiteClasses({TestAltiriaSmsJavaClientSendSmsHttp.class}) // Running this test suite supposes a consumption of credits (between 3 and 5 credits).
//@SuiteClasses({TestAltiriaSmsJavaClientSendSmsAuth.class})

//@SuiteClasses({TestAltiriaSmsJavaClientGetCreditHttp.class})
//@SuiteClasses({TestAltiriaSmsJavaClientGetCreditAuth.class})

// Run all suites (without connection suites)
// Running this test suites supposes a consumption of credits (between 3 and 5 credits).
//@SuiteClasses({TestAltiriaSmsJavaClientSendSmsHttp.class, TestAltiriaSmsJavaClientSendSmsAuth.class, TestAltiriaSmsJavaClientGetCreditHttp.class, TestAltiriaSmsJavaClientGetCreditAuth.class})

public class TestAll {}
