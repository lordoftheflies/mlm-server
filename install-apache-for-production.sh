SITE_NAME="topflavon"
CERTIFICATE_PREFIX=topflavon-
COUNTRY=HU
STATE=Pest
LOCATION=Budapest
ORGANIZATION=DigitalDefense Ltd
ORGANIZATION_UNIT=flavon
DIST=/home/lordoftheflies/Documents/mlm-portal/build/bundled
CATALINA_BASE=/usr/local/apache-tomcat-8.0.27
PASSWORD=qwe123

# Install Apache2
apt-get install apache2 apache2-utils apache2-doc

#cp /etc/apache2/sites-available/000-default.conf /etc/apache2/sites-available/$SITE_NAME.conf
a2dissite topflavon-production.conf

# Enable SSL module.
a2enmod ssl
# Restart service.

# The first step is certificate creation. For testing purposes, or for small LANs, you need to generate a private key (ca.key) with 2048 bit encryption.
# Generate self-signed certificate
openssl genrsa -out ${CERTIFICATE_PREFIX}frontend-ca.key 2048
# Then generate a certificate signing request (ca.csr) using the following command
openssl req -nodes -new -key ${CERTIFICATE_PREFIX}frontend-ca.key -out ${CERTIFICATE_PREFIX}frontend-ca.csr -subj "/C=${COUNTRY}/ST=${STATE}/L=${LOCATION}/O=${ORGANIZATION}/CN=www.${SITE_NAME}.com"
# Lastly, generate a self-signed certificate (ca.crt) of X509 type valid for 365 keys.
openssl x509 -req -days 365 -in ${CERTIFICATE_PREFIX}frontend-ca.csr -signkey ${CERTIFICATE_PREFIX}frontend-ca.key -out ${CERTIFICATE_PREFIX}frontend-ca.crt

# Create SSL directory.
mkdir -p /etc/apache2/ssl
# Next, copy all certificate files to the “/etc/apache2/ssl” directory.
cp ${CERTIFICATE_PREFIX}frontend-ca.crt ${CERTIFICATE_PREFIX}frontend-ca.key ${CERTIFICATE_PREFIX}frontend-ca.csr /etc/apache2/ssl/

#cp /etc/apache2/sites-available/default-ssl.conf ./

cp ./topflavon-production.conf /etc/apache2/sites-available/

ln -s $DIST /var/www/topflavon_portal 

a2ensite topflavon-production.conf

service apache2 restart

/usr/lib/jvm/java-8-oracle/bin/keytool -genkey -alias $SITE_NAME -keyalg RSA -dname "CN=www.${SITE_NAME}.com, OU=${ORGANIZATION_UNIT}, O=${ORGANIZATION}, L=${LOCATION}, S=${STATE}, C=${COUNTRY}" -keystore ${SITE_NAME}-keystore -storepass $PASSWORD -keypass $PASSWORD

cp ./server.xml $CATALINA_BASE/conf/

