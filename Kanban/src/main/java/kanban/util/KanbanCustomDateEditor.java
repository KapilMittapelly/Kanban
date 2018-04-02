package kanban.util;

import java.beans.PropertyEditorSupport;
import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.util.StringUtils;

public class KanbanCustomDateEditor extends PropertyEditorSupport {
	
	private final DateTimeFormatter formatter;
	
	private final boolean allowEmpty;

	private final int exactDateLength;

    /* @Override
     public void registerCustomEditors(PropertyEditorRegistry registry) {
         registry.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
     }*/


	/**
	 * Create a new CustomDateEditor instance, using the given DateFormat
	 * for parsing and rendering.
	 * <p>The "allowEmpty" parameter states if an empty String should
	 * be allowed for parsing, i.e. get interpreted as null value.
	 * Otherwise, an IllegalArgumentException gets thrown in that case.
	 * @param dateFormat DateFormat to use for parsing and rendering
	 * @param allowEmpty if empty strings should be allowed
	 */
	public KanbanCustomDateEditor(DateTimeFormatter formatter, boolean allowEmpty) {
		this.formatter = formatter;
		//this.formatter.
		this.allowEmpty = allowEmpty;
		this.exactDateLength = -1;
	}
	
	/**
	 * Format the Date as String, using the specified DateFormat.
	 */
	@Override
	public String getAsText() {
		try {
			Date value = (Date) getValue();
			if(value != null) {
				LocalDate ld = value.toLocalDate();
				ZonedDateTime zdt = ld.atStartOfDay( ZoneId.of( "America/Montreal" ) ) ;
				return zdt.format(formatter);
			} else {
				return "";
			}
		} catch (DateTimeException ex) {
			throw new IllegalArgumentException("Could not format: " +  ex.getMessage(), ex);
		}
	}
	
	/**
	 * Parse the Date from the given text, using the specified DateFormat.
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			// Treat empty String as null value.
			setValue(null);
		}
		else if (text != null && this.exactDateLength >= 0 && text.length() != this.exactDateLength) {
			throw new IllegalArgumentException(
					"Could not parse date: it is not exactly" + this.exactDateLength + "characters long");
		}
		else {
			try {
				LocalDate date = LocalDate.parse(text, formatter);
				setValue(Date.valueOf(date));
			}
			catch (Exception ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}
	}
	
	public String getAsString(Date value) {
		try {
			if(value != null) {
				LocalDate ld = value.toLocalDate();
				ZonedDateTime zdt = ld.atStartOfDay( ZoneId.of( "America/Montreal" ) ) ;
				return zdt.format(formatter);
			} else {
				return "";
			}
		} catch (DateTimeException ex) {
			throw new IllegalArgumentException("Could not format: " +  ex.getMessage(), ex);
		}
	}

}
