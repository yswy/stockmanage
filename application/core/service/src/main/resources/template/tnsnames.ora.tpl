${databaseCode}=
  (DESCRIPTION =
    (ADDRESS_LIST =
      (ADDRESS = (PROTOCOL = TCP)(HOST = ${databaseAddress})(PORT = ${databasePort}))
    )
    (CONNECT_DATA =
      (SERVICE_NAME = ${serviceName})
      (SERVER = DEDICATED)
    )
  )