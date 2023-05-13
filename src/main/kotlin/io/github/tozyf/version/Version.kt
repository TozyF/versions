package io.github.tozyf.version

/**
 * Represents a parsed version.
 *
 * The implementation of this interface must be immutable.
 */
public interface Version : Comparable<Version> {
    /**
     * Returns the string representation of this version.
     */
    public override fun toString(): String
}

/**
 * Creates an instance of [Version] from the given [versionString]. The created instance is immutable and has no
 * special meaning (has no scheme applied).
 *
 * The comparison of two versions is based on the lexicographic order of their string representations.
 */
public fun Version(versionString: String): Version = ImmutableVersion(versionString)

private class ImmutableVersion(private val versionString: String) : Version {
    override fun toString(): String = versionString

    override fun compareTo(other: Version): Int = versionString.compareTo(other.toString())

    override fun equals(other: Any?): Boolean = other is Version && versionString == other.toString()

    override fun hashCode(): Int = versionString.hashCode()
}
