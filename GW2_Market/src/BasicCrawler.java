  //package edu.uci.ics.crawler4j.examples.basic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */
public class BasicCrawler extends WebCrawler {
	//static int MAX = 11;
	//static int MAX = 13;
	
	static int[] silver_ary = new int[BasicCrawlController.MAX];
	static int[] copper_ary = new int[BasicCrawlController.MAX];
	static int loop = 0, bag_silver = 0, bag_copper = 0;
	static float bag_price = 0, items_total;


        private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
                        + "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

        String text, html;
        /**
         * You should implement this function to specify whether the given url
         * should be crawled or not (based on your crawling logic).
         */
        @Override
        public boolean shouldVisit(WebURL url) {
        	// rate();
                String href = url.getURL().toLowerCase();
                return !FILTERS.matcher(href).matches() && href.startsWith("http://www.gw2spidy.com/item");
        }

        /**
         * This function is called when a page is fetched and ready to be processed
         * by your program.
         */
        @Override
        public void visit(Page page) {
               /* int docid = page.getWebURL().getDocid();
                String url = page.getWebURL().getURL();
                String domain = page.getWebURL().getDomain();
                String path = page.getWebURL().getPath();
                String subDomain = page.getWebURL().getSubDomain();
                String parentUrl = page.getWebURL().getParentUrl();
                String anchor = page.getWebURL().getAnchor();*/

              /*  System.out.println("Docid: " + docid);
                System.out.println("URL: " + url);
                System.out.println("Domain: '" + domain + "'");
                System.out.println("Sub-domain: '" + subDomain + "'");
                System.out.println("Path: '" + path + "'");
                System.out.println("Parent page: " + parentUrl);
                System.out.println("Anchor text: " + anchor);*/
                
                if (page.getParseData() instanceof HtmlParseData) {
                        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                        text = htmlParseData.getText();
                        html = htmlParseData.getHtml();
                       // List<WebURL> links = htmlParseData.getOutgoingUrls();

                      /*  System.out.println("Text length: " + text.length());
                        System.out.println("Html length: " + html.length());
                        System.out.println("Number of outgoing links: " + links.size());*/
                        String the_title = title();
                       if(!(the_title.equals("Supply Bag") || the_title.equals("Stolen Supplies Bag")))
                     	  frame1.text.append(the_title + '\n');
                       else{
                    	   frame1.text.append(the_title + '\n');
                    	   frame1.text.append(bag() + '\n');
                    	   frame1.text.append("Bag price: " + bag_price() + '\n');
                    	   frame1.text.append("total profit: " + find_profit() + '\n');
                    	   frame1.text.append("Total items sell price: " + items_total+ '\n');
                       }
                   
                        price();
                        items_total = totals();
                       
                }

                Header[] responseHeaders = page.getFetchResponseHeaders();
                if (responseHeaders != null) {
                     //   System.out.println("Response headers:");
                        for (Header header : responseHeaders) {
                            //    System.out.println("\t" + header.getName() + ": " + header.getValue());
                        }
                }
                
                frame1.text.append("============= \n");
                
        }
        
        
        
        //Title Method
        public String title(){
        	String title = "";
        	int title_sep = 0;

        	 Document document = Jsoup.parse(html);
        	  for (Element element : document.getElementsByTag("title")) {
        		 title_sep = element.text().indexOf("|");
        	    title = element.text().substring(0, title_sep-1);
        	 }
        	  if (title_sep == -1 || title_sep == 0)
        		  BasicCrawlController.not_found ++;
     	     return title;
        }
        
        
        //Price Method
        public int price(){
    		int gold_flag = 0, silver_flag = 0;
    		System.out.println(silver_flag); //asdasdadsasdadsasdasd
    		String result = "";
    		
        	 int start = text.indexOf("Sell Price:");
             int end = text.indexOf("Buy Price:");
             String str = text.substring(start, end);
             
            // System.out.println(str); 
             frame1.text.append(str + '\n');
             
             // Gold
             int gold_start = str.indexOf(" g");         
             if(!(gold_start == -1)){
            	 gold_flag = 1;
           	 
                 Pattern p = Pattern.compile("\\d+");
                 Matcher m = p.matcher(str);

                m.find();
                     //System.out.println("sell price is: " + m.group(0) + " gold");
                result += m.group(0) + " Gold ";
             }
             
             // Silver
            int silver_start = str.indexOf(" s");
            if(!(silver_start == -1)){
            	silver_flag = 1;
            	
            	Pattern p2 = Pattern.compile("\\d+");
            	Matcher s = p2.matcher(str);

            	 if(gold_flag == 1){
            		 for(int i = 0; i<2; i++)
            			 s.find();
            		             		 
            			 silver_ary[loop] = Integer.parseInt(s.group(0));
            		 //	System.out.println(silver_ary[loop]);
            		 //	for(int j =0; j<MAX; j++)
            		 		//System.out.println("prices array "+j+ " - "+silver_ary[j]);
            		 
            		 result += s.group(0) + " Silver ";
            		 //loop++;
            	 }
            	 
            	 else{
            		 s.find();
            		 silver_ary[loop] = Integer.parseInt(s.group(0));
            		// System.out.println("array of silver: "+silver_ary[loop]);
            		// for(int j =0; j<MAX; j++)
         		 		//System.out.println("prices array "+j+ " index "+silver_ary[j]);
            		 
            		 result += s.group(0) + " Silver ";
            		 //loop++;
            	 }          	 
            }
            
            // Copper
           int copper_start = str.indexOf(" c");
           if(!(copper_start == -1)){
       
        	   Pattern p3 = Pattern.compile("\\d+");
        	   Matcher c = p3.matcher(str);

        	   if(silver_flag == 1 && gold_flag == 1){
          		 	for(int i = 0; i<3; i++)
          		 		c.find();
          		
          		 	copper_ary[loop] = Integer.parseInt(c.group(0));
          		 	//System.out.println("array of copper: "+copper_ary[loop]);
          		 	
          		 //	for(int j =0; j<MAX; j++)
         		 	//	System.out.println("prices array copper "+j+ " - "+copper_ary[j]);
          		 	result += c.group(0) + " Copper";
        	   }
        	   else if(gold_flag == 0 && silver_flag == 1){
         		 	for(int i = 0; i<2; i++)
          		 		c.find();
          		 	
         		 	copper_ary[loop] = Integer.parseInt(c.group(0));
          		 	//System.out.println("array of copper: "+copper_ary[loop]);
          		 	
          			//for(int j =0; j<MAX; j++)
         		 		//System.out.println("prices array copper "+j+ " - "+copper_ary[j]);
        		   	result += c.group(0) + " Copper";
        	   }
        	   else{
        		   c.find();
        		   copper_ary[loop] = Integer.parseInt(c.group(0));
         		 	//System.out.println("array of copper: "+copper_ary[loop]);
         		 	
         		 	//for(int j =0; j<MAX; j++)
         		 		//System.out.println("prices array copper "+j+ " - "+copper_ary[j]);
         		 	
        		   result += c.group(0) + " Copper";
        	   } 
        	   
           }
           //	System.out.println(result);
           	frame1.text.append(result + '\n');
          
           	loop++;
             return end;
        }
        
        public float totals(){
        	float total = 0;
        	float silver_sum = 0; //silver
        	for(int i = 0; i<loop; i++){
        		silver_sum += (silver_ary[i] * BasicCrawlController.drop_rate[i]);
        	
        	}
        	silver_sum *= 100;
        	
        	//copper
        	float copper_sum = 0; //silver
        	for(int i = 0; i<loop; i++){
        		copper_sum += (copper_ary[i] * BasicCrawlController.drop_rate[i]);
        		total += copper_ary[i];
        	}
        	copper_sum *= (100) / 100;
        	total = copper_sum + silver_sum;
        	
        	//System.out.println("total: " + total);
        	frame1.text.append("total: " + total + '\n');
        	return total;
        }
        
        
        // Bag class
        public String bag(){
        	
        	int gold_flag = 0, silver_flag = 0;
    		String result = "";
    		
        	 int start = text.indexOf("Sell Price:");
             int end = text.indexOf("Buy Price:");
             String str = text.substring(start, end);
             
             
             // Gold
             int gold_start = str.indexOf(" g");         
             if(!(gold_start == -1)){
            	 gold_flag = 1;
           	 
                 Pattern p = Pattern.compile("\\d+");
                 Matcher m = p.matcher(str);

                m.find();
                     //System.out.println("sell price is: " + m.group(0) + " gold");
                result += m.group(0) + " Gold ";
             }
             
             // Silver
            int silver_start = str.indexOf(" s");
            if(!(silver_start == -1)){
            	silver_flag = 1;
            	
            	Pattern p2 = Pattern.compile("\\d+");
            	Matcher s = p2.matcher(str);

            	 if(gold_flag == 1){
            		 for(int i = 0; i<2; i++)
            			 s.find();
            		       
            		 bag_silver = Integer.parseInt(s.group(0));
            		 //loop++;
            	 }
            	 
            	 else{
            		 s.find();
            		 bag_silver = Integer.parseInt(s.group(0));
         		 
            		 result += s.group(0) + " Silver ";
            		 //loop++;
            	 }          	 
            }
            
            // Copper
           int copper_start = str.indexOf(" c");
           if(!(copper_start == -1)){
       
        	   Pattern p3 = Pattern.compile("\\d+");
        	   Matcher c = p3.matcher(str);

        	   if(silver_flag == 1 && gold_flag == 1){
          		 	for(int i = 0; i<3; i++)
          		 		c.find();
          		
          		 	bag_copper = Integer.parseInt(c.group(0));
          		 	result += c.group(0) + " Copper";
        	   }
        	   else if(gold_flag == 0 && silver_flag == 1){
         		 	for(int i = 0; i<2; i++)
          		 		c.find();
          		 	
         		 	bag_copper = Integer.parseInt(c.group(0));
        		   	result += c.group(0) + " Copper";
        	   }
        	   else{
        		   c.find();
        		   bag_copper = Integer.parseInt(c.group(0));
         		 	
        		   result += c.group(0) + " Copper";
        	
        	   }
           }
           return bag_silver +" Silver " + bag_copper + " Copper";
        }
        
        public float bag_price(){
        	bag_price = bag_silver * 100;
        	bag_price += bag_copper;
        	
        	return bag_price;
        }
        
        public float find_profit(){
        	float bag, items, result;
        	bag = bag_price();
        	items = items_total-(items_total*0.15f);
        	
        	result = items - bag;
        	return result;
        }
       
       static public void clean(){
        silver_ary = null;
        copper_ary = null;
        loop = 0;
        bag_silver = 0;
        bag_copper = 0;
    	bag_price = 0;
    	System.out.println("clean");
        }
}