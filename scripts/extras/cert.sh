
openssl s_client -showcerts -verify 5 -connect server_dimain_url:443 < /dev/null |
   awk '/BEGIN CERTIFICATE/,/END CERTIFICATE/{ if(/BEGIN CERTIFICATE/){a++}; out="cert"a".pem"; print >out}'
for cert in *.pem; do
        newname=$(openssl x509 -noout -subject -in $cert | sed -nE 's/.*CN ?= ?(.*)/\1/; s/[ ,.*]/_/g; s/__/_/g; s/_-_/-/; s/^_//g;p' | tr '[:upper:]' '[:lower:]').pem
        echo "${newname}"; mv "${cert}" "${newname}"
done

#Run this shell file to get a fullchain to install in nginx
# sh cert.sh
#cat server_dimain_url.pem sectigo_rsa_organization_validation_secure_server_ca.pem usertrust_rsa_certification_authority.pem > fullchain.pem