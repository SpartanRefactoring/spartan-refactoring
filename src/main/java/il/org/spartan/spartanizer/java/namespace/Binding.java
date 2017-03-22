package il.org.spartan.spartanizer.java.namespace;

import org.eclipse.jdt.core.dom.*;

import il.org.spartan.spartanizer.engine.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Information about a variable in the environment - its {@link ASTNode}, its
 * parent's, its {@link type}, and which other variables does it hide. This
 * class is intentionally package level, and intentionally defined local. For
 * now, clients should not be messing with it
 * @since 2016 */
public class Binding {
  private static boolean eq(@Nullable final Object o1, @Nullable final Object o2) {
    return o1 == o2 || o1 == null && o2 == null || o2.equals(o1);
  }

  /** For Information purposes, {@link type}s are equal if their key is
   * equal. */
  private static boolean eq(@Nullable final type t1, @Nullable final type t2) {
    return t1 == null ? t2 == null : t2 != null && t1.key().equals(t2.key());
  }

  /** What do we know about an entry hidden by this one */
  @Nullable final Binding hiding;
  /** The node at which this entry was created */
  @Nullable private final ASTNode self;
  /** What do we know about the type of this definition */
  @Nullable private final type type;

  public Binding() {
    hiding = null;
    type = null;
    self = null;
  }

  @NotNull @Override public String toString() {
    return type + "";
  }

  public Binding(@SuppressWarnings("unused") final ASTNode blockScope, final Binding hiding, final ASTNode self, final type type) {
    this.hiding = hiding;
    this.self = self;
    this.type = type;
  }

  public Binding(final type type) {
    this.type = type;
    self = null;
    hiding = null;
  }

  public Binding(@SuppressWarnings("unused") final String key, final type type) {
    this.type = type;
    hiding = null;
    self = null;
  }

  public Binding(@SuppressWarnings("unused") final String key, final ASTNode self) {
    this.self = self;
    hiding = null;
    type = null;
  }

  private boolean equals(@NotNull final Binding ¢) {
    return eq(hiding, ¢.hiding) && eq(type, ¢.type) && eq(self, ¢.self);
  }

  /** @param ¢
   * @return whether the ASTNode (self) and its parent (blockScope) are the same
   *         ones, the type's key() is the same, and if the Information nodes
   *         hidden are equal. */
  // Required for MapEntry equality, which is, in turn, required for Set
  // containment check, which is required for testing.
  @Override public boolean equals(@Nullable final Object ¢) {
    return ¢ == this || ¢ != null && getClass() == ¢.getClass() && equals((Binding) ¢);
  }

  // Required for MapEntry equality, which is, in turn, required for Set
  // containment check, which is required for testing.
  @Override public int hashCode() {
    return (self == null ? 0 : self.hashCode()) + 31 * ((hiding == null ? 0 : hiding.hashCode()) + 31);
  }
}