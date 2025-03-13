package seedu.address.storage;

import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Role;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {
    public static final String INVALID_TAG_TYPE = "Unknown tag type found";
    private final String tagName;
    private String tagType = TagTypes.TAG.serialisedName;

    /**
     * Constructs a {@code JsonAdaptedTag} of type {@code Tag} with the given {@code tagName}.
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public JsonAdaptedTag(String tagName) {
        this(tagName, TagTypes.TAG.serialisedName);
    }

    /**
     * Constructs a {@code JsonAdaptedTag} of type {@code tagType} with the given {@code tagName}.
     * See: {@link TagTypes} for types
     */
    @JsonCreator
    public JsonAdaptedTag(@JsonProperty("tagName") String tagName, @JsonProperty("tagType") String tagType) {
        this.tagName = tagName;
        this.tagType = tagType;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        tagName = source.tagName;
        if (source instanceof Role) {
            tagType = TagTypes.ROLE.serialisedName;
        }
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        try {
            return TagTypes.getConstructorFromName(tagType).apply(tagName);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }
    }

    /**
     * Enumeration defining the valid subclasses of {@code Tag} that can be saved via Jackson.
     */
    private enum TagTypes {
        TAG("tag", Tag::new),
        ROLE("role", Role::new);

        public final String serialisedName;
        public final Function<String, Tag> constructor;
        TagTypes(String serialisedName, Function<String, Tag> tagConstructor) {
            this.serialisedName = serialisedName;
            this.constructor = tagConstructor;
        }

        public static Function<String, Tag> getConstructorFromName(String typeName) throws IllegalValueException {
            for (TagTypes type : TagTypes.values()) {
                if (type.serialisedName.equals(typeName)) {
                    return type.constructor;
                }
            }
            throw new IllegalValueException(INVALID_TAG_TYPE);
        }
    }
}
