package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@TeleOp(name="Vuforia Test")
//@Disabled
public class VisionJavaExample extends LinearOpMode{
    MasterVision vision;
    SampleRandomizedPositions goldPosition;

    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;// recommended camera direction
        parameters.vuforiaLicenseKey = "ARJCp6T/////AAABmRttT+LHV03viaux59tDgwQAcMq1HFTvZNKn5yFhA+l2VltLOSTPHHtHahoM9BTEmQKSs31iPWOjUw6PquYvKi/swRXOSNvJdHzqT7NvkcAiS8tHg/oV7YYATIbGItnLWdKdAVxxCdyTEsAhpNjSPB13B9F9cN6k4tYr38faOz51bbINpcKd6jivqJDwatyuaU2r9F5eSERe2GrzZfSIqUCZdW3tDIhXCgsJ1U4AS6QdYspg0yoG88VsxAZHNZvEl2Ldc7tenqS2MBLBSORv8uQisk6wgqJSlv4oOnoQoMd9p72+cAV2HUO5I1uynCeR/ON8oSMxfmaa4spc51p8Ek7EK7mtaEy+SgkSDC/EYSQ8";

        vision = new MasterVision(parameters, hardwareMap, false, MasterVision.TFLiteAlgorithm.INFER_LEFT);
        vision.init();// enables the camera overlay. this will take a couple of seconds
        vision.enable();// enables the tracking algorithms. this might also take a little time

        waitForStart();

        vision.disable();// disables tracking algorithms. this will free up your phone's processing power for other jobs.

        goldPosition = vision.getTfLite().getLastKnownSampleOrder();

        while(opModeIsActive()){
            telemetry.addData("goldPosition was", goldPosition);// giving feedback

            switch (goldPosition){ // using for things in the autonomous program
                case LEFT:
                    telemetry.addLine("going to the left");
                    break;
                case CENTER:
                    telemetry.addLine("going straight");
                    break;
                case RIGHT:
                    telemetry.addLine("going to the right");
                    break;
                case UNKNOWN:
                    telemetry.addLine("staying put");
                    break;
            }

            telemetry.update();
        }

        vision.shutdown();
    }
}
