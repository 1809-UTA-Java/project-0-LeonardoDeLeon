package main.java;

//import org.junit.Test;
import org.junit.*;

public class BankAccountTest {
	
	BankAccount testAccount = new BankAccount();
	
	@Test
	public void testDeposit() {
		//replace fail with
		Assert.assertEquals("normal positive test",testAccount.deposit(50),true);
	}
	
    @Test
    public void testDepositWithNegative() {
        //replace fail with
        Assert.assertEquals("for negative values",testAccount.deposit(-50),true);  // fail with negative numbers
    }
    @Test(expected = NullPointerException.class)
    public void testDepositNullAmt() {
        Assert.assertEquals(testAccount.deposit(0),true);  // expect it to fail 
    
    }
//	

}
