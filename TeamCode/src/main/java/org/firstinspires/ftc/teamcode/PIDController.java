package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by wardp on 2/18/2018.
 */

public class PIDController {

    private double integrated_error;
    private double[] last_error = new double[5]; // Store the last few recorded errors and average to discard momentary data.

    private double lastOutput = 0;

    private LinearOpMode opMode;

    private Parameters parameters;

    public PIDController(LinearOpMode opMode, PIDController.Parameters params) {

        this.opMode = opMode;

        this.parameters = params;
    }

    public double update(double current) {

        double error = current - parameters.target;

        double output = parameters.kP * error + parameters.kI * getIntegratedError() + parameters.kD * getDeltaError(error);
        opMode.telemetry.addData("PID Output:", Double.toString(output));

        recordError(error);

        lastOutput = output;

        return Range.clip(output, -parameters.maxPower, parameters.maxPower);
    }

    private double getIntegratedError() {
        return integrated_error;
    }

    private double getDeltaError(double error) {
        double average_error = 0;

        for (double aLast_error : last_error) average_error += aLast_error;

        average_error /= last_error.length;

        return average_error - error;

    }

    private void recordError(double error) {
        integrated_error += error; // Record for integral term
        shiftData(error); // Record for derivative term
    }

    private void shiftData(double new_data) {
        System.arraycopy(last_error, 1, last_error, 0, last_error.length - 1);
        last_error[last_error.length - 1] = new_data;
    }

    public boolean complete() {
        opMode.telemetry.addData("Last Error", last_error[0]);
        opMode.telemetry.addData("ToleranceCount", parameters.tolerance);

        // We're finished if we're within tolerance and the output is small
        return Math.abs(last_error[0]) < parameters.tolerance && Math.abs(lastOutput) < .1;
    }

    public static class Parameters {
        double kP, kI, kD, target, tolerance, maxPower;

        public Parameters(double kP, double kI, double kD, double target, double tolerance, double maxPower) {
            this.kP = kP;
            this.kI = kI;
            this.kD = kD;
            this.target = target;
            this.tolerance = tolerance;
            this.maxPower = maxPower;
        }
    }
}
