package com.tsh.vas.service;

import com.tsh.vas.service.area.AreaServiceTest;
import com.tsh.vas.service.business.AreaSupplierServiceTest;
import com.tsh.vas.service.business.BusinessServiceTest;
import com.tsh.vas.service.charge.ChargeServiceTest;
import com.tsh.vas.service.supplier.SupplierServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The test suite that runs all tests.
 *
 * @author iritchie.ren
 * @version 1.1
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({AreaSupplierServiceTest.class, SupplierServiceTest.class, AreaServiceTest.class, BusinessServiceTest.class, ChargeServiceTest.class})
public class TestSuite {}