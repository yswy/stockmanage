W=`date +%Y%m%d`
echo "****************************************************"
echo "* Building"
TIME=`date +%H:%M:%S`
echo "* DATE: $NOW - $TIME"
echo "****************************************************"

# building
cd /home/admin/build/application
mvn clean install

echo "****************************************************"
echo "* Building ---- end "
TIME=`date +%H:%M:%S`
echo "* DATE: $NOW - $TIME"
echo "****************************************************"
exit 0;
