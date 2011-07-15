/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.compat.cdi.visibility;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Validates that a bean in the WEB-INF/classes directory is visible to the BeanManager injected into a bean contained within a
 * bean library in the same bean (web) archive.
 *
 * @author <a href="http://community.jboss.org/people/dan.j.allen">Dan Allen</a>
 * @see <a href="http://java.net/jira/browse/GLASSFISH-15721">GLASSFISH-15721</a> (unresolved)
 */
@RunWith(Arquillian.class)
public class VisibilityOfBeanInWebModuleFromBeanManagerInBeanLibraryTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        JavaArchive jar1 = ShrinkWrap.create(JavaArchive.class, "a.jar")
                .addClasses(Beer.class, BeerCollector.class, American.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

        return ShrinkWrap.create(WebArchive.class, "test.war").addClass(CraftBeer.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml").addAsLibrary(jar1);
    }

    @Test
    public void shouldFindBeanByType(BeerCollector collector) {
        assertThat(collector.getNumDiscovered(), equalTo(2));
    }

    @Test
    public void shouldFindBeanByName(BeerCollector collector) {
        assertThat(collector.isNamedBeerVisible("americanCraftBeer"), is(true));
    }
}
