# WeatherAppMvi
This project is another implementation of: https://github.com/SergJ4/WeatherSoramitsu

Here I've created same app but using MVI pattern. Actually I got a monster MVVM - MVI. 
I'm using Google Architecture ViewModel to preserve MVI feature inside it, because MVICore (https://github.com/badoo/MVICore) does not provide any activity rotation survival mechanisms.

Project consist of 4 modules + application module. Core module does not depend on any other module. All other modules depends on Core. Application depends on all (Core transitive through Repo)

Technologies stack:
- Dagger2 with AndroidInjector
- Google Architecture ViewModel
- MVICore (https://github.com/badoo/MVICore)
- RxJava2
- Retrofit
- Cicerone Navigator (https://github.com/terrakok/Cicerone)
