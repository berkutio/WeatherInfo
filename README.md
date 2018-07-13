# WeatherInfo
Opensource application for obtaining weather forecast based on device GPS coordinates. Application architectures implements MVP approach.
<p align="center">
  <img src="https://github.com/berkutio/WeatherInfo/blob/master/device-2018-07-13-141500.png" width="350" height="650"/>
</p>

Brief description:
<a href="https://github.com/berkutio/WeatherInfo/tree/jetpack_clean_architecture"># jetpack_clean_architecture barnch</a>

Solution for generating one-time error messages while using live data and Google architecture components

# master branch:

Using one network request only helps better understand MVP feathres as well as it's testing ability.

# dagger branch:

Geolocation and Network dependencies were rewrited with Dagger2 library included. Thus master and dagger branches could be compared and possible examples of Dagger2 applying could be noticed. As it can be seen, Dagger2 helps to simplify complex dependencies configuration, plus reduces code strings quantity in the WeatherActivity class.

# rx branch:

Simple replacing return param in retrofit interface class.

# mvpdatabinding branch:

Implemented databinding for both Activity and Adapter. Given approach shows that databinding can be applied not only for MVVM architecture but also for MVP.

# mvvmdatabinding branch:

Implemented mvvm pattern with the help of databinding. Reduced amount of bolierplate code in the project.


