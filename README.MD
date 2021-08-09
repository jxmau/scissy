<h1>Scissy</h1>

<h3> What is it? </h3>
Scissy is a small project of a weather static website for the channel's islands.

<h3> How does it work? </h3>
Scissy is a server side rendering website with an independent database actualized automatically.

<h3> Dependencies </h3>

* Spring Web and Spring Boot Starter
* Apache HttpComponents 
* JSON Simple
* Thymeleaf

<h3> Threads </h3>

| Name | Description | Permanent |
| ---- | ----------- | --------- |
| Éther | Fetch Air Pollution information using OpenWeatherMap's Current Air Pollution Condition API. | Yes |
| Zeus | Fetch Current Weather Condition using OWM's Current Weather Condition API.| Yes |
| Janus | Fetch Forecast Weather using OWM's One Call API. | Yes |
| Charon | Delete weather's entries that are now in the past | No. It launches at each Current Weather's actualization. |
