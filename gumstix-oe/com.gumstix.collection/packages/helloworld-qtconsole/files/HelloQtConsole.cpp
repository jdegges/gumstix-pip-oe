#include <QCoreApplication>
#include <QDebug>


int main(int argc, char** argv)
{
	QCoreApplication app(argc, argv);

	qDebug() << "Hello world";

	return app.exec();
}

