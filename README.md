# WeatherInfo

On the "Google I/O 2018" Google has preseted Android JetPack - set of libraries, which can help make the app development faster. Android LiveData - well known part of JetPack, which eliminates all the pain, related to device rotation and data restoring. LivaData does it's job well, however, there is some issue: LiveData class intented for supporting the data in up-to-date mode and in cases, where it is necessary to provide any one-time actions, it can be quite challanging to implement it.

The code, presented in this branch, shows one of the approaches, aimed to solve one-time actions problems while using LivaData library.
