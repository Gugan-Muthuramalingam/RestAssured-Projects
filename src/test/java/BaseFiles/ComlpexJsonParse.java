package BaseFiles;

import io.restassured.path.json.JsonPath;

public class ComlpexJsonParse {
	public static void main(String[] args) {
		JsonPath js1 = new JsonPath(PayLoad.CoursePrice());

		int count = js1.getInt("courses.size()");
		System.out.println(count);

		int totalAmount = js1.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);

		String title = js1.get("courses[0].title");
		System.out.println(title);

		for (int i = 0; i < count; i++) {
			String courseTitle = js1.get("courses[" + i + "].title");
			int priceTitle = js1.get("courses[" + i + "].price");

			System.out.println(courseTitle);
			System.out.println(priceTitle);
		}

		for (int i = 0; i < count; i++) {
			String copiesCount = js1.get("courses[" + i + "].title");
			if (copiesCount.equalsIgnoreCase("RPA")) {
				int copies = js1.get("courses[" + i + "].copies");
				System.out.println(copies);

			}
		}
	}
}