# miniReddit

It’s a backend application similar to social networking forum Reddit. 

## Features:
•	User can sign-up to application. <br/>
•	User can create communities i.e, subreddits eg, r/coding which can be a community for discussion on coding topics.<br/>
•	User can create multiple posts within the subreddit. Also can see posts from other subreddits.<br/>
•	User can follow a subreddit, to get the posts from followed subreddits on the feed/home-page.<br/>
•	User can Upvote or Download on a post.<br/>
•	User can bookmark/save the post.<br/>
•	User can comment on the posts.<br/>
•	For Authentication – JWT token along with Refresh token is used for each request<br/>

## System Design:

The architecture used for the application is monolithic architecture with abstract layering of Controller, Service and DataRepository.<br/>
The System uses Relational DB (MySQL) for all the services.
               
## APIs:
| REST Endpoint | Method | Param/Request Body | Description |
| --- | --- | --- | --- |
| /auth/signup | POST | User Details | Sign Up |
| /auth/login | POST | User Credentials | Login And Return back JWT token along with Refresh Token |
| /auth/logout | POST | Refresh Token | Delete Refresh Token and invalide JWT token |
| /api/user/get-detais/{id} | GET | User Id | Get User Details |
| /api/user/update | PUT | User Details | Update User Details |
| /api/subreddit | POST | Subreddit Details | Create New Subreddit |
| /api/subreddi/{id} | GET | Subreddit Id | Get Subreddit Details-Number Of Posts, Created Dates, ..etc |
| /api/subreddit | GET | --- | Get All Subreddits |
| /api/user/follow-subreddit/{Id} | POST | Subreddit Id | Follow Subreddit |
| /api/posts | POST | Post Details With Subreddit Name | Create Post In Subreddit |
| /api/posts/{Id} | GET | Post Id | Get Post With Id |
| /api/posts/user/{Id}?sortBy={SortAttr}&page={PageNumber}&size=PageSize | GET | User Id, Page Size, Page Number, SortAttr, PageNumber, PageSize | Get All Posts Made By Particular User With Pagination And Sorting  |
| /api/posts/subreddit/{Id}?sortBy={SortAttr}&page={PageNumber}&size=PageSize | GET | Subreddit Id, Page Size, Page Number, SortAttr, PageNumber, PageSize | Get All Posts From Particular Subreddit With Pagination And Sorting  |
| /api/comments | POST | Comment Body With Username And PostId | Comment On Particular Post |
| /api/comments/post/{postId} | GET | Post Id | Get All Comments On Particular Post |
| /api/comments/user/{userId} | GET | User Id | Get All Comments By Particular User |
| /api/user/posts/bookmark | POST | Post Id, User Id | Bookmark Particular Post |
| /api/user/posts/bookmark/{userId} | GET | User Id | Get All Posts Bookmarked By User |
| /api/votes | POST | Vote Request | Vote On A Post |
| /api/user/feed/{userId} | GET | User Id | Generate Feed/Homepage For User From The Subreddits Followed By That User |

### DB Schema:
![alt text](https://github.com/jatinKhachane/miniReddit/blob/main/miniReddit_Arch_v2.png)      

## Tech Stack :

This backend system is developed using – <br/>
•	Java<br/>
• Spring Boot<br/>
•	Spring Data<br/>
•	Spring Security-JWT(for authentication)<br/>
• MySQL<br/>

## Tools Used :
•	Postman(API testing)<br/>
•	MySQL workbench(MySQL GUI dashboard)<br/>
•	IntelliJ Idea IDE<br/>
