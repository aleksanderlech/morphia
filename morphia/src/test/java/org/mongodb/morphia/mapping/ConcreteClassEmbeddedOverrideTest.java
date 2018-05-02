package org.mongodb.morphia.mapping;


import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.mongodb.morphia.TestBase;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

public class ConcreteClassEmbeddedOverrideTest extends TestBase {

    @Test
    public void test() {
        final E e1 = new E();
        Assert.assertEquals("A", e1.a1.s);
        Assert.assertEquals("A", e1.a2.s);

        getDatastore().save(e1);

        final E e2 = getDatastore().get(e1);

        Assert.assertEquals("A", e2.a1.s);
        Assert.assertEquals("A", e2.a2.s);
        Assert.assertEquals(B.class, e2.a2.getClass());
        Assert.assertEquals(A.class, e2.a1.getClass());

    }

    public static class E {
        private final A a1 = new A();
        @Property(concreteClass = B.class)
        private final A a2 = new A();
        @Id
        private ObjectId id;
    }

    @Embedded
    public static class A {
        private String s = "A";

        public String getS() {
            return s;
        }

        public void setS(final String s) {
            this.s = s;
        }
    }

    public static class B extends A {
        public B() {
            setS("B");
        }
    }
}
