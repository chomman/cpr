package cz.nlfnorm.config.utils;

import org.junit.Assert;
import org.junit.Test;

import cz.nlfnorm.validators.PasswordValidator;

@SuppressWarnings("static-access")
public class TestPasswordValidator {
	
	
	@Test
    public void testNormalPassword()
    {
        PasswordValidator validator = PasswordValidator.buildValidator(false, false, false, 6, 14);
 
        Assert.assertTrue(validator.validatePassword("howtodoinjava"));
        Assert.assertTrue(validator.validatePassword("howtodoin"));
        //Sort on length
        Assert.assertFalse(validator.validatePassword("howto"));
    }
 
    @Test
    public void testForceNumeric()
    {
        PasswordValidator validator = PasswordValidator.buildValidator(false,false, true, 6, 16);
        //Contains numeric
        Assert.assertTrue(validator.validatePassword("howtodoinjava12"));
        Assert.assertTrue(validator.validatePassword("34howtodoinjava"));
        Assert.assertTrue(validator.validatePassword("howtodo56injava"));
        //No numeric
        Assert.assertFalse(validator.validatePassword("howtodoinjava"));
        Assert.assertFalse(validator.validatePassword("123456"));
    }
 
    @Test
    public void testForceCapitalLetter()
    {
        PasswordValidator validator = PasswordValidator.buildValidator(false,true, false, 6, 16);
        //Contains capitals
        Assert.assertTrue(validator.validatePassword("howtodoinjavA"));
        Assert.assertTrue(validator.validatePassword("Howtodoinjava"));
        Assert.assertTrue(validator.validatePassword("howtodOInjava"));
        //No capital letter
        Assert.assertFalse(validator.validatePassword("howtodoinjava"));
    }
 
    @Test
    public void testForceSpecialCharacter()
    {
        PasswordValidator validator = PasswordValidator.buildValidator(true,false, false, 6, 16);
        //Contains special char
        Assert.assertTrue(validator.validatePassword("howtod@injava"));
        Assert.assertTrue(validator.validatePassword("@Howtodoinjava"));
        Assert.assertTrue(validator.validatePassword("howtodOInjava@"));
        //No special char
        Assert.assertFalse(validator.validatePassword("howtodoinjava"));
    }
    
}
