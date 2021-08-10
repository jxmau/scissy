<h1>Scissy</h1>

[![CodeFactor](https://www.codefactor.io/repository/github/jxmau/scissy/badge?s=66708753caf93a38dae0894e0a0335a06a505a23)](https://www.codefactor.io/repository/github/jxmau/scissy)

<h3> What is it? </h3>
Scissy is a small project of a weather static website for the channel's islands. 

Currently deployed on [Heroku](https://scissy.herokuapp.com)


<h3> How does it work? </h3>
Scissy is a server side rendering website with an independent database actualized automatically.

<h3> Stack </h3>

* Java SDK 16
* HTML5 / CSS3
* Postgres Database
* Deployed on Heroku

<h4> Dependencies used </h4>

* Spring Web and Spring Boot Starter
* Apache HttpComponents 
* JSON Simple
* Thymeleaf

<h4> APIs used </h4>

* OpenWeatherMap's One Call.
* OpenWeatherMap's Current Weather.
* OpenWeatherMap's Air Pollution.

<h3> Threads </h3>

| Name | Description | Permanent |
| ---- | ----------- | --------- |
| Éther | Fetch Air Pollution information using OpenWeatherMap's Current Air Pollution Condition API. | Yes |
| Zeus | Fetch Current Weather Condition using OWM's Current Weather Condition API.| Yes |
| Janus | Fetch Forecast Weather using OWM's One Call API. | Yes |
| Charon | Delete passed hourly weather entries | No. <br> A thread is launched each time the Current Weather entries are actualized. |


<h3> Environement Variables </h3>


| Key | Descriptions |
| ---  | --- |
| SCISSY_DB | URL of the database. |
| SCISSY_DB_USERNAME | Username of the db. |
| SCISSY_DB_PASSWORD | Password of the user of the db. |
| OWM_KEY | App Key for OpenWeatherMap's APIs. |

<h3> License </h3>

Scissy is under the DWTFYWTPL license. See the LICENSE.MD for more non-information.