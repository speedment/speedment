## Speedment Build
This module contains submodules we have set up to make it easier to use Speedment in various deployment scenarios. For an example, if you want to use the `runtime` package on a webserver, you might want the shaded .jar with all the dependencies included. In that case, you can simply depend on the `runtime-deploy` jar.

This module also contains the maven plugin used to run Speedment as a maven goal.
