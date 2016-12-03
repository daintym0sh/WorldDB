/**
 * Namespace information for the World Bank XML data
 */
@XmlSchema(
        namespace="http://www.worldbank.org",
        elementFormDefault=XmlNsForm.QUALIFIED)
package main.datasources.worldbank;

import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;