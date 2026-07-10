# Jenkins Shared Library for Java Pipeline

This shared library follows Jenkins best practices for enterprise usage.

## Structure

- vars/: contains the declarative pipeline entrypoint and reusable Jenkins steps.
- src/navi/company/utils/: contains pure Groovy helper classes with no Jenkins DSL.

## Flow

1. The Jenkinsfile calls the shared-library entrypoint.
2. vars/javaPipeline.groovy defines the declarative pipeline and orchestrates stages.
3. Each stage calls reusable vars methods.
4. Helper classes under src provide plain utility logic.

## Structure

- vars/: reusable functions that contain the shell commands and Jenkins step logic.
- src/com/company/pipeline/JavaPipeline.groovy: orchestration class that defines the declarative pipeline stages.
- Jenkinsfile: minimal entrypoint that loads the library and passes configuration.

## How it works

1. The Jenkinsfile loads the shared library and calls the JavaPipeline class.
2. The JavaPipeline class defines the declarative pipeline and coordinates stages.
3. Each stage invokes a reusable function from vars/.
4. The vars files contain the implementation details and commands.

## Example usage

In the Jenkinsfile, configure the values and call the pipeline with a map.

## Notes

- Replace the library name in the Jenkinsfile if your Jenkins configuration uses a different shared library name.
- Ensure the Jenkins agent label and credentials exist in your Jenkins environment.
