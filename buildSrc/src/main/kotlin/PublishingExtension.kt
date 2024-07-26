import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

fun PublishingExtension.configurationLibrary(project: Project, artifactId: String) {
    publications {
        create("bootJava", MavenPublication::class.java) {
            this.artifactId = artifactId
            from(project.components.getByName("java"))
        }
    }
}
