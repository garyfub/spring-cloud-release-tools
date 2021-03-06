package org.springframework.cloud.release.internal.pom;

import edu.emory.mathcs.backport.java.util.Arrays;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstraction over collection of projects
 *
 * @author Marcin Grzejszczak
 */
public class Projects extends HashSet<ProjectVersion> {

	public Projects(Set<ProjectVersion> versions) {
		addAll(versions);
	}

	@SuppressWarnings("unchecked")
	public Projects(ProjectVersion... versions) {
		addAll(new HashSet<ProjectVersion>(Arrays.asList(versions)));
	}

	public ProjectVersion forFile(File projectRoot) {
		final ProjectVersion thisProject = new ProjectVersion(projectRoot);
		return this.stream().filter(projectVersion -> projectVersion.projectName.equals(thisProject.projectName))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Project with name [" + thisProject.projectName + "] is not present"));
	}

	public ProjectVersion forName(String projectName) {
		return this.stream().filter(projectVersion -> projectVersion.projectName.equals(projectName))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Project with name [" + projectName + "] is not present"));
	}

	public boolean containsSnapshots() {
		return this.stream().anyMatch(ProjectVersion::isSnapshot);
	}
}
