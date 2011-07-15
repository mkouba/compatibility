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
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

/**
 * Same as {@link JarToJarAlphaVisibilityTest} but the content of alpha and bravo jars is swapped to detect ordering issues.
 *
 * @author <a href="mailto:jharting@redhat.com">Jozef Hartinger</a>
 * @see <a href="http://java.net/jira/browse/GLASSFISH-15735">GLASSFISH-15735</a> (unresolved)
 */
@RunWith(Arquillian.class)
public class JarToJarReverseAlphaVisibilityTest {
    @Deployment
    public static WebArchive getDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
        war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        war.addAsLibrary(create(JavaArchive.class, "bravo.jar").addClass(Foo.class).addAsManifestResource(EmptyAsset.INSTANCE,
                "beans.xml"));
        war.addAsLibrary(create(JavaArchive.class, "alpha.jar").addClass(Bar.class).addAsManifestResource(EmptyAsset.INSTANCE,
                "beans.xml"));
        return war;
    }

    @Test
    public void testDeployment() {
        // noop
    }
}
