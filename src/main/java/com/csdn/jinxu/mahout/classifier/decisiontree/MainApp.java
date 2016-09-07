package com.csdn.jinxu.mahout.classifier.decisiontree;

import org.apache.mahout.classifier.df.DecisionForest;
import org.apache.mahout.classifier.df.data.Data;
import org.apache.mahout.classifier.df.data.DescriptorException;

/**
 * 实现描述：${todo} 类描述待完成
 *
 * @author jin.xu
 * @version v1.0.0
 * @see
 * @since 16-8-31 下午8:54
 */
public class MainApp {
    private static final String[] TRAIN_DATA = {"sunny,85,85,FALSE,no",
            "sunny,80,90,TRUE,no", "overcast,83,86,FALSE,yes",
            "rainy,70,96,FALSE,yes", "rainy,68,80,FALSE,yes", "rainy,65,70,TRUE,no",
            "overcast,64,65,TRUE,yes", "sunny,72,95,FALSE,no",
            "sunny,69,70,FALSE,yes", "rainy,75,80,FALSE,yes", "sunny,75,70,TRUE,yes",
            "overcast,72,90,TRUE,yes", "overcast,81,75,FALSE,yes",
            "rainy,71,91,TRUE,no"};

    private static final String[] TEST_DATA = {"rainy,68,80,FALSE,-",
            "overcast,64,65,TRUE,-", "sunny,75,70,TRUE,-",};

    private static final String DESCRIPTOR="C N N C L";


    public static void main(String[] args) throws DescriptorException {

        Data data=DecisionTreeClassify.generateTrainingData(DESCRIPTOR, false, TRAIN_DATA);
        DecisionForest forest=DecisionTreeClassify.train(data);

        Data testData=DecisionTreeClassify.generateTrainingData(DESCRIPTOR, false, TEST_DATA);

        double[][] predictions=new double[testData.size()][];
        forest.classify(testData,predictions);
        for(int testId=0;testId<testData.size();++testId){
            for(int treeId=0;treeId<predictions[testId].length;++treeId) {
                double noValue = testData.getDataset().valueOf(4, "no");
                double yesValue = testData.getDataset().valueOf(4, "yes");
                System.out.println("test dataset " + testId + ": " + predictions[testId][treeId]+",noValue:"+noValue+",yesValue:"+yesValue);
            }
        }
    }
}
