package org.uji.agile.contactsbook.tests.acceptance;

import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.PrintStreamStepMonitor;
import org.junit.runner.RunWith;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

@RunWith(JUnitReportingRunner.class)
public class ContactsBookStories extends JUnitStories {

	public ContactsBookStories() {
        Configuration configuration = new MostUsefulConfiguration()
        .usePendingStepStrategy(new FailingUponPendingStep())
        .useStoryLoader(new LoadFromClasspath(this.getClass().getClassLoader()))
        .useStepMonitor(new PrintStreamStepMonitor())
        ;
        useStepsFactory(new InstanceStepsFactory(configuration,new ContactsBookSteps()));
     }

	@Override
	protected List<String> storyPaths() {
        return new StoryFinder().findPaths(CodeLocations.codeLocationFromPath("src/test/resources"),
                        "acceptance/*.story", "");
    }

}