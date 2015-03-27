package de.hhn.labswps.wefactor.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SSLConfig {

    @Value("${enableHTTPS}")
    Boolean enableHTTPS;

    @Value("${keystoreFile}")
    String keystoreFile;

    @Value("${keystorePass}")
    String keystorePass;

    @Value("${keystoreType}")
    String keystoreType;

    @Value("${keystoreProvider}")
    String keystoreProvider;

    @Value("${keystoreAlias}")
    String keystoreAlias;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {

        // keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize
        // 2048 -keystore keystore.p12 -validity 3650
        // keytool -list -v -keystore keystore.p12 -storetype pkcs12

        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();

        if (enableHTTPS) {
            factory.addConnectorCustomizers((TomcatConnectorCustomizer) (
                    Connector con) -> {
                con.setScheme("https");
                con.setSecure(true);
                Http11NioProtocol proto = (Http11NioProtocol) con
                        .getProtocolHandler();
                proto.setSSLEnabled(true);
                proto.setKeystoreFile(keystoreFile);
                proto.setKeystorePass(keystorePass);
                proto.setKeystoreType(keystoreType);
                proto.setProperty("keystoreProvider", keystoreProvider);
                proto.setKeyAlias(keystoreAlias);
            });
        }

        return factory;
    }

}
