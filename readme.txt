API   
Develop automation tests to check status and response using Java and any library of your choice.  
 
1.	Check that GET call to 
https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J?term=Java 
returns response with at least these parts:
•	4 suggestions contain attribute “term” : value starting with the preformatted highlighted word java inside like <span class=\"search-highlight\">java</span>
•	4 pages with attribute “title”: value includes word Wiley 

2.	There is a simple HTTP Request & Response Service https://httpbin.org. Suggest tests that verify the below end point works as intended:
https://httpbin.org/#/Dynamic_data/post_delay__delay_ 
POST/delay/{delay} returns a delayed response (max of 10 seconds).

3.	Suggest tests that verify the below end point works as intended:
https://httpbin.org/#/Images/get_image_png
GET/image/png returns a simple PNG image
