all:
	mvn eclipse:eclipse

clean:
	mvn eclipse:clean
	mvn clean
	rm -rf .setting
