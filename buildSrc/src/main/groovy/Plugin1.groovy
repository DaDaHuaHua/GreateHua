import org.gradle.api.Plugin
import org.gradle.api.Project

class Plugin1 implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println 'plugin1'

        def android = project.extensions.create('android2', Android2Extensions)
        project.android2.extensions.create('defaultConfig', DefaultExtensions)
        //gradle分析完成之后才能获取参数
        project.afterEvaluate {
            println project.android2.compileSdkVersion
            println project.android2.defaultConfig.applicationId
            MyTask task = project.tasks.create("hello ", MyTask)
            task.dependsOn project.tasks.getByName("checkFreeAlphaManifest")
//            project.tasks.getByName("preFreeAlphaBuild").dependsOn task
        }
    }
}