package com.csdn.jinxu.weka.classifier.svm;


import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instances;

/**
 * 实现描述：J48操作入口
 *
 * @author jin.xu
 * @version v1.0.0
 * @see
 * @since 16-9-1 下午6:23
 */
public class MainApp {
    private static String TRAIN_FILE_NAME="/home/jinxu/utils/src/main/resources/soybean.arff";
    private static String TEST_FILE_NAME="/home/jinxu/utils/src/main/resources/soybean.arff";

    public static void main(String[] args) throws Exception {

        Instances trainInstances= WekaSVMClassifier.generate(TRAIN_FILE_NAME, null);

        AttributeSelectedClassifier classifier = WekaSVMClassifier.train(trainInstances, null);

        Instances testInstances= WekaSVMClassifier.generate(TEST_FILE_NAME, null);

        System.out.println(testInstances.instance(0).toString());
        classifier.distributionForInstance(testInstances.instance(0));
    }
}
