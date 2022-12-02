package complexJson;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class complexjson {

	@Test
	public void courses ()
	{
		JsonPath js= new JsonPath(payload.courseprice());
		//	Print No of courses returned by API
		int count=js.getInt("courses.size()");      //size() is used for array only
		System.out.println(count);
		//	Print Purchase Amount
		int totalAmount=js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		//	Print Title of the first course
		String title=js.get("courses[0].title");
		System.out.println(title);
		//	Print All course titles and their respective Prices
		for(int i=0;i<count;i++)
		{
			String alltitle=js.get("courses["+i+"].title");
			System.out.println(alltitle);	
			System.out.println(js.get("courses["+i+"].price").toString());
		}

		System.out.println("Print no of copies sold by RPA Course");
		for(int i=0;i<count;i++)
		{
			String alltitle=js.get("courses["+i+"].title");
			if(alltitle.equalsIgnoreCase("RPA"))
			{
				int copies=js.get("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
		}	
	}

	@Test
	public void sumofcourse()
	{
		int sum=0;
		JsonPath js= new JsonPath(payload.courseprice());
		int count=js.getInt("courses.size()");
		for(int i=0;i<count;i++)
		{
			int price=js.getInt("courses["+i+"].price");
			int copies= js.getInt("courses["+i+"].copies");
			int amount= price*copies;
			System.out.println(amount);
			sum= sum+amount;		

		}	
		System.out.println(sum);	
		int purchaseamount=js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum,purchaseamount);
	}	
}
