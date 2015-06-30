# RenewIT Deliverable D2.2

Prototype files for [Deliverable 2.2](Green%20Management%20of%20DC_models.pdf) of [RenewIT project](http://www.renewit-project.eu/): Green management of Data Centres.

This prototype releases a model for energy and ecological efficiency assessment, as well as the initial specification of the algorithms for energy-driven run-time optimisation of local VM placement and Data Centre selection for remote VM placement, focusing on how they use the model for energy and ecological efficiency assessment.

The prototype is structured as follows:

*	`monitoringFramework` contains a metric collection framework developed at BSC to gather power and performance metrics, in addition to the metrics that are already provided by the third-party monitoring agents.
*	`powerModeling/datasets` contains the data that has been collected during the execution of benchmarks in different host environments (two different models of AMD Opteron and three different models of Intel Xeon processors).
*	`powerModeling/prototype` contains the prototype implementation of the power model generator that allows to estimate and predict the power consumption of a server.
*	`algorithms/clopla` contains the Clopla java library that, given a set of virtual machines and hosts, computes the optimized placement for the VMs.


This work is supported by the European Union under FP7-SMARTCITIES-2013
contract 608679.

