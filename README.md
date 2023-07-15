# News Sentiment Analysis Project
This project is a Spring Boot microservice project that fetches news articles from an external source based on the provided query parameter and performs sentiment analysis using Chat GPT. It leverages various technologies and features to achieve efficient and accurate sentiment analysis on news articles. The aim of the project is to analyze the sentiment of news articles and provide insights into the overall sentiment of the content.

# Features
The project offers the following features:

1. News Fetching: Users can provide a query parameter to specify the topic or keyword for which they want to fetch news articles. The project utilizes an external API to fetch the latest news related to the provided query.

2. Sentiment Analysis: The fetched news articles are then passed to the Chat GPT language model for sentiment analysis. The language model analyzes the text and determines the sentiment of each article as positive, negative, or neutral.

# News Sentiment Analysis Workflow
![News Sentiment Analysis Workflow](https://github.com/HarshitTamrakar/News-aggregator-and-sentiment-analysis/assets/39623432/a8a6b1eb-346a-4d0b-b35c-5697e4b5b1e2)

# API endpoints
API Documentation
Search News Articles
This endpoint allows you to search for news articles based on a query parameter and perform sentiment analysis on the fetched articles.

**Endpoint: GET /api/news/search**

**Query Parameters:**

| Parameter	| Type |	Description |	Required |
|-----------|------|--------------|----------|
| query |	string |	The keyword or topic to search for news articles. |	Yes |
| sentimentAnalysis |	boolean |	Flag to indicate whether to perform sentiment analysis on the fetched articles. | No |

**Curl Request:**
```shell
curl --location 'http://localhost:8081/api/news/search?query=facebook&sentimentAnalysis=true'
```

**Response:** 

The API response returns a list of news articles based on the search query. Each news article object contains the following properties:

| Property |	Type |	Description |
|----------|-------|--------------|
|id	| integer |	The unique identifier of the news article.|
| title |	string |	The title or headline of the news article. |
| text |	string |	The content or body of the news article. |
| url |	string |	The URL of the news article.|
| language |	string |	The language of the news article. |
| author |	string |	The author of the news article. |
| publish_date |	string |	The date and time when the news article was published. |
| source_country |	string | 	The country of the news article's source. |
| analysisResult |	object |	The sentiment analysis results for the news article.|

The analysisResult object provides the following sentiment analysis results:

| Property |	Type |	Description |
|----------|-------|--------------|
| positive |	string |	The percentage of positive sentiment in the article. |
| negative |	string |	The percentage of negative sentiment in the article. |
| neutral |	string |	The percentage of neutral sentiment in the article. |
| message |	string |	Additional message related to the sentiment analysis. |

**Example Response:**
```shell
{
    "news": [
        {
            "id": 126956930,
            "title": "Biden's gang of social media censors exposed by federal judge",
            "text": "A federal judge who excoriated the Biden administration for pressuring social media platforms to censor Americans’ speech shined a light on a group of obscure but powerful White House staffers who leaned on Twitter, Facebook, YouTube and other sites to remove posts and ban users whose content they opposed. U.S. District Judge Terry A. Doughty, acting on a lawsuit filed against the Biden administration by two states and a group of plaintiffs, said the case “arguably involves the most massive attack against free speech in United States’ history.” Court documents show that while top Biden administration officials such as Dr. Anthony Fauci sought publicly and privately to censor social media posts over COVID-19 content, the task was more extensively carried out behind the scenes by a select band of staffers. These aides led the administration’s efforts to squelch content they opposed, mostly by pressuring social media platforms with repeated requests for content removal, de-platforming of specific users and relentless demands for access to their internal content moderation policies and practices. The effort began almost as soon as Mr. Biden entered the White House with a Jan. 23, 2021, email from Clarke Humphrey, then the digital director for the administration’s COVID-19 response team. Mr. Humphrey emailed Twitter officials at 1 a.m. on Mr. Biden’s third day in office and asked them to remove a tweet posted a day earlier by Robert F. Kennedy Jr. that suggested, without evidence, that the death of Hank Aaron, 86, could be tied to the coronavirus vaccine. SEE ALSO: Whistleblowers: FBI failed to warn agents on social media censorship “Hey folks — Wanted to flag the below tweet and am wondering if we can get moving on the process of having it removed ASAP,” Mr. Humphrey wrote to Twitter. The job of policing social media appeared to fall mostly to Mr. Humphrey’s White House colleague, Rob Flaherty, who recently left the administration, reportedly to take a job in President Biden’s reelection campaign. Mr. Humphrey had looped in Mr. Flaherty on the Jan. 23 email to Twitter, requesting he “keep an eye out for tweets that fall in this same genre.” Mr. Flaherty, who served as deputy assistant to the president and director of digital strategy, subsequently led a dogged campaign to coerce Twitter, Facebook and other social media platforms to remove content about COVID-19 that went against the Biden administration’s policies, especially on posts and content that were skeptical of the vaccines or pandemic-related mandates. Mr. Flaherty continually pressured Facebook to share with the White House the company’s internal policies for removing or moderating content. He demanded Facebook take more aggressive action to censor “borderline” anti-vaccine content, which included posts that did not violate the platform’s rules but made the administration uncomfortable. Mr. Flaherty in March 2021 sent Facebook officials a media report about Facebook’s internal study on the link between vaccine hesitancy and “borderline” Facebook content. Such content included Facebook posts about experiencing or fearing severe vaccine side effects. Mr. Flaherty accused Facebook of “hiding the ball” from the White House by not turning over the platform’s internal study information, to which Facebook responded that the media report did not accurately convey the research they are conducting. “I don’t think this is a misunderstanding,” Mr. Flaherty wrote to Facebook in response. “I’ve been asking you guys pretty directly, over a series of conversations, for a clear accounting of the biggest issues you are seeing on your platform when it comes to vaccine hesitancy, and the degree to which borderline content — as you define it — is playing a role.” In the exchange, Mr. Flaherty accused Facebook of allowing social media posts that spurred the Jan. 6, 2021, Capitol riot. He demanded to know “what actions and changes you are making to ensure you are not making our country’s vaccine hesitancy problem worse.” While Mr. Flaherty strong-armed Twitter and Facebook to cooperate with the White House, another staffer, Andy Slavitt, ramped up the effort by threatening the social media platforms with federal action. Mr. Slavitt at the time was serving as a senior adviser to the Biden administration’s pandemic response team and was copied in on Mr. Flaherty’s emails to Facebook. He followed up a message to the social media giant, warning them that “internally, we have been considering our options on what to do” about the platform’s failure to comply with White House demands. Mr. Slavitt didn’t say specifically what the administration was considering. The federal government can’t control private social media platforms but could hobble them significantly by working with Congress to eliminate their liability shield, known as Section 230 of the Communications Decency Act. The 1996 law protects them from legal liability over the content posted on their sites. Mr. Slavitt also played a role in coercing Twitter to de-platform Alex Berenson, a former New York Times reporter who during the pandemic posted questions, concerns and research about the mRNA-based vaccine’s side effects. Mr. Berenson’s tweets also highlighted the research and data demonstrating the limited efficacy of the vaccines, lockdowns, masks and other Biden administration pandemic policies. According to court documents, Mr. Slavitt, during a White House meeting with Twitter officials in April 2021, called Mr. Berenson “the epicenter of disinfo that radiated outwards to the persuadable public.” Mr. Berenson was suspended by Twitter on July 16, 2021, and de-platformed on Aug. 28, 2021. Mr. Berenson, who successfully sued Twitter to reactivate his account, is suing Mr. Slavitt and other White House officials as well as two senior board members of the vaccine maker Pfizer, over their bid to silence him. “The White House was particularly concerned about me as someone whose questions could not be dismissed as mere conspiracy theories or paranoid delusions,” Mr. Berenson told The Washington Times. “They targeted me because — not in spite of — the fact that I presented reasonable, data-driven objections to mRNA vaccinations for young people and for mandates.” Mr. Slavitt, who left the administration in June 2021, did not respond to a request for comment. Upon his departure from the White House, Mr. Slavitt introduced Surgeon General Vivek Murthy to senior Facebook officials to help Dr. Murthy carry out the administration’s quest to quash COVID-19 social media content it opposed. Eric Waldo, who is a senior adviser to Dr. Murthy, led the effort to implement Dr. Murthy’s July 2021 “health advisory on misinformation.” The advisory aimed to “stop the spread of misinformation on social media platforms” related to COVID-19. Mr. Waldo worked to ensure Facebook, Twitter, Instagram, Google and YouTube turned over internal company data on the content they labeled as misinformation as well as the steps taken by them to censor information that was critical of the vaccine or the Biden administration’s pandemic policies. In August 2021, The surgeon general’s staff ramped up the pressure on Facebook, giving the platform a two-week deadline to provide the information. Facebook responded with the report “How We’re Taking Action Against Vaccine Misinformation Superspreaders.” It included a detailed list of steps it had taken to block content posted by a White House-promoted list of users called the “Disinformation Dozen.” The dozen social media accounts were identified in March 2021 by the nonprofit Center for Countering Digital Hate, which has ties to the left-wing British Labor Party. The group worked to get conservative commentator Katie Hopkins banned from Twitter in the U.K. and attempted to coerce Google into de-platforming the U.S. conservative website The Federalist. The center’s list of a dozen offending posters included Mr. Kennedy, a longtime vaccine skeptic who is now a Democratic presidential candidate. The White House promoted the center’s list of a dozen offenders as part of Dr. Murthy’s “misinformation” health advisory. “There are about 12 people who are producing 65% of vaccine misinformation on social media platforms,” then-White House press secretary Jen Psaki said on July 15, 2021. In addition to Mr. Kennedy, the list of offending users included osteopath physician Sherri Tenpenny, who the center cited for posting on Facebook a study that concluded cloth masks are ineffective and may increase the risk of infection. Facebook in response provided Mr. Waldo with two additional reports in September 2021 on its efforts to silence the so-called misinformation on COVID policies and mute some of the pages and posts of the dozen offending platforms. Mr. Waldo sought similar updates from Twitter, Instagram, Google and YouTube, according to court documents. Carol Crawford, the Centers for Disease Control and Prevention’s digital media director, also helped carry out the White House’s censorship scheme. The court documents detail how she began holding weekly meetings in January 2021 with Facebook’s content mediation team to discuss “misinformation” about the vaccines. She looped in Census Bureau officials, who partnered with the CDC on the effort and wanted to discuss “misinformation topics,” including concerns about the vaccine causing side effects, infertility and death. Ms. Crawford repeatedly emailed Facebook about specific postings that she deemed as misinformation on both Facebook and Instagram, which are both owned by Meta. According to court documents, Facebook “began to rely on Crawford and the CDC to determine whether claims were true or false,” including whether the virus had a 99.96% survival rate, whether the vaccine caused Bells’ palsy and whether people who were administered the shot were part of a medical experiment. Under the direction of Ms. Crawford, Facebook would remove or censor claims the CDC said were false. Ms. Crawford also had regular contact with Twitter over posts that she and CDC experts believed were misinformation. She sent Twitter a list of content the CDC, working with the Census Bureau, identified as false. The list included posts alleging the vaccines were not approved by the FDA, posts about “fraudulent cures,” vaccine injury data “taken out of context,” and claims the COVID shot caused infertility. Ms. Crawford, according to the court documents, “understood she was flagging the posts for Twitter for possible censorship.” Twitter responded to Ms. Crawford that some of the offending posts had been “reviewed and actioned.” The behind-the-scenes efforts by the White House and the Biden administration to censor social media content were effective. The platforms began removing content and banning users almost as soon as Biden administration officials began contacting them and they set up special portals that allowed White House and administration staffers to collaborate with the companies over content moderation. The Biden administration is seeking to halt Judge Daughtry’s injunction. White House officials defended their actions and said they were aimed at preserving public health, safety, and security during a deadly pandemic. The Justice Department Monday filed an emergency stay motion in a federal appeals court after Judge Daughtry, who was nominated to the bench by President Trump, denied the department’s initial request to lift the ban on communicating with social media outlets.",
            "url": "https://www.washingtontimes.com/news/2023/jul/11/bidens-gang-social-media-censors-exposed-federal-j/",
            "language": "en",
            "author": "Susan Ferrechio",
            "analysisResult": {
                "positive": "15",
                "negative": "75",
                "neutral": "10",
                "message": null
            },
            "publish_date": "2023-07-11 18:24:00",
            "source_country": "us"
        }
    ]
}
```

# Deployment
