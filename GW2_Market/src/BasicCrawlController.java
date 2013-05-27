import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.util.IO;

//package edu.uci.ics.crawler4j.examples.basic;


/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */
public class BasicCrawlController {
	
	private static final Logger logger = Logger.getLogger(CrawlController.class.getName());
	public static int which_bag = 1, not_found = 0;
	static Float[] drop_rate = new Float[] {0.065f, 0.0678f, 0.1816f, 0.271f, 0.0759f, 0.0244f, 0.0596f, 0.0678f, 0.0623f, 0.0515f, 0.0732f }; // Supply bag
	static int MAX = 0;
	
       //public static void main(String[] args) throws Exception {
	public void startThis(String def, String num, String bagNum) throws Exception{
        	
                if (def.equals(null) || num.equals(null)) {
                     System.out.println("Needed parameters: ");
                        System.out.println("\t rootFolder (it will contain intermediate crawl data)");
                        System.out.println("\t numberOfCralwers (number of concurrent threads)");
                        return;
                }

                which_bag = Integer.parseInt(bagNum);
                /*
                 * crawlStorageFolder is a folder where intermediate crawl data is
                 * stored.
                 */
                String crawlStorageFolder = def;

                /*
                 * numberOfCrawlers shows the number of concurrent threads that should
                 * be initiated for crawling.
                 */
                int numberOfCrawlers = Integer.parseInt(num);

                CrawlConfig config = new CrawlConfig();

                config.setCrawlStorageFolder(crawlStorageFolder);

                /*
                 * Be polite: Make sure that we don't send more than 1 request per
                 * second (1000 milliseconds between requests).
                 */
                config.setPolitenessDelay(200);

                /*
                 * You can set the maximum crawl depth here. The default value is -1 for
                 * unlimited depth
                 */
                config.setMaxDepthOfCrawling(0);

                /*
                 * You can set the maximum number of pages to crawl. The default value
                 * is -1 for unlimited number of pages
                 */
                config.setMaxPagesToFetch(20);

                /*
                 * Do you need to set a proxy? If so, you can use:
                 * config.setProxyHost("proxyserver.example.com");
                 * config.setProxyPort(8080);
                 * 
                 * If your proxy also needs authentication:
                 * config.setProxyUsername(username); config.getProxyPassword(password);
                 */

                /*
                 * This config parameter can be used to set your crawl to be resumable
                 * (meaning that you can resume the crawl from a previously
                 * interrupted/crashed crawl). Note: if you enable resuming feature and
                 * want to start a fresh crawl, you need to delete the contents of
                 * rootFolder manually.
                 */
                config.setResumableCrawling(false);

                /*
                 * Instantiate the controller for this crawl.
                 */
                PageFetcher pageFetcher = new PageFetcher(config);
                RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
                RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
                CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

                
                boolean resumable = config.isResumableCrawling();
                

                // my change
                EnvironmentConfig envConfig = new EnvironmentConfig();
                envConfig.setAllowCreate(true);
                envConfig.setTransactional(resumable);
                envConfig.setLocking(resumable);
                
                
                // end my change

 

                /*
                 * For each crawl, you need to add some seed urls. These are the first
                 * URLs that are fetched and then the crawler starts following links
                 * which are found in these pages
                 */
               
                
                if(which_bag  == 1){
                    //  Float[] drop_rate = new Float[] {0.065f, 0.0678f, 0.1816f, 0.271f, 0.0759f, 0.0244f, 0.0596f, 0.0678f, 0.0623f, 0.0515f, 0.0732f }; // Supply bag
                    drop_rate = new Float[] {0.065f, 0.0678f, 0.1816f, 0.271f, 0.0759f, 0.0244f, 0.0596f, 0.0678f, 0.0623f, 0.0515f, 0.0732f }; // Supply bag
                    MAX = 11;
                	
                controller.addSeed("http://www.gw2spidy.com/item/24344", 2); //Bone
                controller.addSeed("http://www.gw2spidy.com/item/24348"); //Claw
                controller.addSeed("http://www.gw2spidy.com/item/19730"); //Coarse Leather Section
                controller.addSeed("http://www.gw2spidy.com/item/19741"); //Cotton Scrap
                controller.addSeed("http://www.gw2spidy.com/item/24354"); //Fang
                controller.addSeed("http://www.gw2spidy.com/item/24307"); //Onyx Fragment
                controller.addSeed("http://www.gw2spidy.com/item/24274"); //Pile of Radiant Dust
                controller.addSeed("http://www.gw2spidy.com/item/24286"); //Scale
                controller.addSeed("http://www.gw2spidy.com/item/24298"); //Totem
                controller.addSeed("http://www.gw2spidy.com/item/24280"); //Venom Sac
                
                controller.addSeed("http://www.gw2spidy.com/item/9281"); //Supply bag
                }
                
                if(which_bag  == 2){ // Stolen Supplies bag
                    // Float[] drop_rate = {0.0744f, 0.0f, 0.0694f, 0.1389f, 0.1677f, 0.0843f, 0.0992f, 0.0754f, 0.0f, 0.0665f, 0.0635f, 0.0883f, 0.0724f }; //Stolen Supply bag
                    drop_rate = new Float[] {0.0744f, 0.0f, 0.0694f, 0.1389f, 0.1677f, 0.0843f, 0.0992f, 0.0754f, 0.0f, 0.0665f, 0.0635f, 0.0883f, 0.0724f }; //Stolen Supply bag
                    MAX = 13;
                    
                    controller.addSeed("http://www.gw2spidy.com/item/24344"); //Bone
                    controller.addSeed("http://www.gw2spidy.com/item/12229"); //Chocolate Bar
                    controller.addSeed("http://www.gw2spidy.com/item/24348"); //Claw
                    controller.addSeed("http://www.gw2spidy.com/item/19730"); //Coarse Leather Section
                    controller.addSeed("http://www.gw2spidy.com/item/19741"); //Cotton Scrap
                    controller.addSeed("http://www.gw2spidy.com/item/24354"); //Fang
                    controller.addSeed("http://www.gw2spidy.com/item/12351"); //Orange
                    controller.addSeed("http://www.gw2spidy.com/item/24286"); //Scale
                    controller.addSeed("http://www.gw2spidy.com/item/12138"); //Stick of Butter
                    controller.addSeed("http://www.gw2spidy.com/item/24298"); //Totem
                    controller.addSeed("http://www.gw2spidy.com/item/24292"); //Vial of Blood
                    controller.addSeed("http://www.gw2spidy.com/item/24280"); //Venom Sac
                    controller.addSeed("http://www.gw2spidy.com/item/24274"); //Pile of Radiant Dust

                    controller.addSeed("http://www.gw2spidy.com/item/9305"); //Stolen Supplies bag
                	
                }
                

                /*
                 * Start the crawl. This is a blocking operation, meaning that your code
                 * will reach the line after this only when crawling is finished.
                 */
                
                controller.start(BasicCrawler.class, numberOfCrawlers);
                
                frame1.text.append("Title errors: " + not_found);
                
                //start my change
                File envHome = new File(config.getCrawlStorageFolder() + "/frontier");
                
                if (!envHome.exists())         
                	if (!envHome.mkdir()) 
                		throw new Exception("Couldn't create this folder: " + envHome.getAbsolutePath());       
                
                if (!resumable) 
                	IO.deleteFolderContents(envHome);
                
                Environment env = new Environment(envHome, envConfig);
                
                if(!resumable) 
                	deleteExistingDatabases(env);

// en my change
                
                
                
                controller.shutdown();
                controller.waitUntilFinish();
                System.out.println("Finished");
        }
	
	public int getBag(){
		return which_bag;
	}
	
	private void deleteExistingDatabases(Environment env) {
        try {
            List<String> databases = env.getDatabaseNames();
            for (String database : databases) {
                logger.debug("removing database '" + database + "'");
                env.removeDatabase(null, database);
            }
        } catch (DatabaseException e) {
            logger.warn("Error while deleteing databases, ignoring...", e);
        }
    }

        
}
