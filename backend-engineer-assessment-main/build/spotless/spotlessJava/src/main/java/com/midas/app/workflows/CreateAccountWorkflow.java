package com.midas.app.workflows;

import com.midas.app.activities.AccountActivity;
import com.midas.app.models.Account;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface CreateAccountWorkflow {
  String QUEUE_NAME = "create-account-queue";

  /**
   * createAccount creates a new account in the system or provider.
   *
   * @param details is the details of the account to be created.
   * @return Account
   */
  @WorkflowMethod
  Account createAccount(Account details);
}

class CreateAccountWorkflowImpl implements CreateAccountWorkflow {
  private final AccountActivity accountActivity =
      Workflow.newActivityStub(AccountActivity.class, ActivityOptions.newBuilder().build());

  @Override
  public Account createAccount(Account account) {
    // Save account in the data store
    Account savedAccount = accountActivity.saveAccount(account);

    // Create payment account in the payment provider
    return accountActivity.createPaymentAccount(savedAccount);
  }
}
